package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.component.Maps;
import org.jwybw.battlecity.component.blocks.*;
import org.jwybw.battlecity.component.entities.BotTank;
import org.jwybw.battlecity.component.entities.Bullet;
import org.jwybw.battlecity.component.entities.Tank;
import org.jwybw.battlecity.image.AdaptType;
import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;
import org.jwybw.battlecity.image.ImageUtil;
import org.jwybw.battlecity.toolkit.MapBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapPanel {
	private final BufferedImage
		grassImage = new BufferedImage(416, 416, BufferedImage.TYPE_INT_ARGB),
		ironImage = new BufferedImage(416, 416, BufferedImage.TYPE_INT_ARGB),
		gravelImage = new BufferedImage(416, 416, BufferedImage.TYPE_INT_ARGB);
	private final ImageEngine imageEngine = new ImageEngine(EngineType.MENU | EngineType.WORD, 1);
	public int playerOneLeftLife = 2, playerTwoLeftLife = 2;
	public int[][] mapArray = new int[26][26];
	public List<Bullet> bulletList = new ArrayList<>();
	public List<Block> grassList = new ArrayList<>(); //子弹穿透 坦克穿透
	public List<Block> ironList = new ArrayList<>(); //子弹不穿透 坦克不穿透
	public List<Block> brickList = new ArrayList<>(); //子弹不穿透 坦克不穿透
	public List<Block> gravelList = new ArrayList<>(); //子弹穿透 坦克穿透
	public List<Block> riverList = new ArrayList<>(); //子弹穿透 坦克穿透
	public Block eagleBlock;
	public List<Tank> tankList = new ArrayList<>();//坦克类
	public List<BotTank> botTankList = new ArrayList<>();//bot坦克类
	public List<Thread> threads = new ArrayList<>();
	public Tank player1, player2;
	public int level;
	public Maps map;
	private BufferedImage mapImage;
	private Graphics2D mapGraphics;

	public MapPanel(int level) {
		this.level = level;
		mapImage = new BufferedImage(416 + 70, 416, BufferedImage.TYPE_INT_RGB);
		mapGraphics = mapImage.createGraphics();

		//初始化地图
		try {
			map = MapBuilder.readMap(level);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Block> blockList = map.blockList;
		for (Block block : blockList) {
			if (block instanceof GrassBlock) {
				grassList.add(block);
				Graphics2D grassGraphics = grassImage.createGraphics();
				grassGraphics.drawImage(block.getImage(), block.getX(), block.getY(), null);
				grassGraphics.dispose();
			} else if (block instanceof IronBlock) {
				ironList.add(block);
				Graphics2D ironGraphics = ironImage.createGraphics();
				ironGraphics.drawImage(block.getImage(), block.getX(), block.getY(), null);
				ironGraphics.dispose();
				mapArray[block.getY() / 16][block.getX() / 16] = 1;
			} else if (block instanceof BrickBlock) {
				brickList.add(block);
				mapArray[block.getY() / 16][block.getX() / 16] = 2;
			} else if (block instanceof GravelBlock) {
				gravelList.add(block);
				Graphics2D gravelGraphics = gravelImage.createGraphics();
				gravelGraphics.drawImage(block.getImage(), block.getX(), block.getY(), null);
				gravelGraphics.dispose();
			} else if (block instanceof RiverBlock) {
				riverList.add(block);
				mapArray[block.getY() / 16][block.getX() / 16] = 3;
				mapArray[block.getY() / 16 + 1][block.getX() / 16] = 3;
				mapArray[block.getY() / 16][block.getX() / 16 + 1] = 3;
				mapArray[block.getY() / 16 + 1][block.getX() / 16 + 1] = 3;
			} else if (block instanceof EagleBlock) {
				eagleBlock = block;
			}
		}

		//0：路 1：铁块 2：砖块 3：河流 4：temp
		for (int y = 22; y < 25; y++) {
			for (int x = 10; x < 15; x++) {
				if (mapArray[y][x] == 5 || mapArray[y][x] == 2) {
					mapArray[y][x] = 0;
				}
			}
		}
	}

	public void startThread(Thread thread) {
		threads.add(thread);
		thread.start();
	}

	public void closeThreads() {
		for (Thread thread : threads) {
			thread.interrupt();
		}
	}

	//0：路 1：铁块 2：砖块 3：河流 4：tanks 5：走不了的路 6：temp砖 7：temp坦克
	public void updateMapArray(BotTank theBotTank, boolean avoidBrick) {
		if (!avoidBrick) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0, brickListSize = brickList.size(); i < brickListSize; i++) {
				Block brickBlock = brickList.get(i);
				mapArray[brickBlock.getY() / 16][brickBlock.getX() / 16] = 6;
			}
		}
		for (BotTank botTank : botTankList) {
			if (theBotTank == botTank) {
				continue;
			}
			mapArray[botTank.getY() / 16][botTank.getX() / 16] = 7;
			mapArray[botTank.getY() / 16 + 1][botTank.getX() / 16] = 7;
			mapArray[botTank.getY() / 16][botTank.getX() / 16 + 1] = 7;
			mapArray[botTank.getY() / 16 + 1][botTank.getX() / 16 + 1] = 7;
		}
		for (int y = 0; y < mapArray.length; y++) {
			for (int x = 0; x < mapArray[y].length; x++) {
				if (mapArray[y][x] == 2 || mapArray[y][x] == 4 || mapArray[y][x] == 5) {
					mapArray[y][x] = 0;
				} else if (mapArray[y][x] == 6) {
					mapArray[y][x] = 2;
				} else if (mapArray[y][x] == 7) {
					mapArray[y][x] = 4;
				}

				if (mapArray[y][x] == 0) {
					if (x == 25 || y == 25 ||
						mapArray[y + 1][x] != 0 && mapArray[y + 1][x] != 5 ||
						mapArray[y][x + 1] != 0 && mapArray[y][x + 1] != 5 ||
						mapArray[y + 1][x + 1] != 0 && mapArray[y + 1][x + 1] != 5
					) {
						mapArray[y][x] = 5;
					}
				}
			}
		}

		for (int y = 22; y < 25; y++) {
			for (int x = 10; x < 15; x++) {
				if (mapArray[y][x] == 5 || mapArray[y][x] == 2) {
					mapArray[y][x] = 0;
				}
			}
		}
	}

	public BufferedImage toImage() {
		mapImage = new BufferedImage(416 + 70, 416, BufferedImage.TYPE_INT_RGB);
		mapGraphics = mapImage.createGraphics();

		//河流
		for (Block river : riverList) {
			mapGraphics.drawImage(river.getImage(), river.getX(), river.getY(), null);
		}

		//砂砾
		mapGraphics.drawImage(gravelImage, 0, 0, null);

		//铁
		mapGraphics.drawImage(ironImage, 0, 0, null);

		//砖
		for (int i = 0; i < brickList.size(); i++) {
			Block brick = brickList.get(i);
			if (brick.isExist()) {
				mapGraphics.drawImage(brick.getImage(), brick.getX(), brick.getY(), null);
			} else {
				brickList.remove(i);
				i--;
			}
		}

		mapGraphics.drawImage(eagleBlock.getImage(), eagleBlock.getX(), eagleBlock.getY(), null);

		//Tank
		for (int i = 0; i < tankList.size(); i++) {
			Tank tank = tankList.get(i);
			if (tank.isVisible()) {
				ImageUtil.paintXY(mapGraphics, tank.getImage(), tank.getBounds());
				// mapGraphics.drawImage(tank.getImage(), tank.getX(), tank.getY(), null);
			} else {
				tankList.remove(i);
				i--;
			}
		}
		for (int i = 0; i < botTankList.size(); i++) {
			BotTank botTank = botTankList.get(i);
			if (botTank.isVisible()) {
				ImageUtil.paintXY(mapGraphics, botTank.getImage(), botTank.getBounds());
				// mapGraphics.drawImage(botTank.getImage(), botTank.getX(), botTank.getY(), null);
			} else {
				botTankList.remove(i);
				i--;
			}
		}

		//子弹
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);
			if (bullet.isVisible()) {
				ImageUtil.paintXY(mapGraphics, bullet.getImage(), bullet.getBounds());
				// mapGraphics.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), null);
			} else {
				bulletList.remove(i);
				i--;
			}
		}

		//草
		mapGraphics.drawImage(grassImage, 0, 0, null);

		//侧边栏
		BufferedImage sidebarImage = ImageUtil.toBufferedImage(imageEngine.Menu_Sidebar);
		Graphics2D sidebarGraphics = sidebarImage.createGraphics();

		for (int i = 0; i < map.unusedBot; i++) {
			sidebarGraphics.drawImage(imageEngine.Icon_tank, 8 + i % 2 * 8, 24 + i / 2 * 8, null);
		}
		for (int i = map.unusedBot; i < 20; i++) {
			sidebarGraphics.drawImage(imageEngine.Icon_gray, 8 + i % 2 * 8, 24 + i / 2 * 8, null);
		}
		//16 144
		BufferedImage playerOneLeftLifeBi;
		switch (playerOneLeftLife % 10) {
			case 0 -> playerOneLeftLifeBi = imageEngine.GameWord_0;
			case 1 -> playerOneLeftLifeBi = imageEngine.GameWord_1;
			case 2 -> playerOneLeftLifeBi = imageEngine.GameWord_2;
			case 3 -> playerOneLeftLifeBi = imageEngine.GameWord_3;
			case 4 -> playerOneLeftLifeBi = imageEngine.GameWord_4;
			case 5 -> playerOneLeftLifeBi = imageEngine.GameWord_5;
			case 6 -> playerOneLeftLifeBi = imageEngine.GameWord_6;
			case 7 -> playerOneLeftLifeBi = imageEngine.GameWord_7;
			case 8 -> playerOneLeftLifeBi = imageEngine.GameWord_8;
			case 9 -> playerOneLeftLifeBi = imageEngine.GameWord_9;
			default -> throw new IllegalStateException("Unexpected value: " + playerOneLeftLife % 10);
		}
		playerOneLeftLifeBi = ImageUtil.adapt(playerOneLeftLifeBi, new Color(117, 117, 117), 9, 8, AdaptType.FitCenter);
		sidebarGraphics.drawImage(playerOneLeftLifeBi, 16, 144, null);

		if (player2 == null) {
			//	10 160
			sidebarGraphics.drawImage(ImageUtil.makePureImage(15, 16, new Color(117, 117, 117)), 9, 160, null);
		} else {
			BufferedImage playerTwoLeftLifeBi;
			switch (playerTwoLeftLife % 10) {
				case 0 -> playerTwoLeftLifeBi = imageEngine.GameWord_0;
				case 1 -> playerTwoLeftLifeBi = imageEngine.GameWord_1;
				case 2 -> playerTwoLeftLifeBi = imageEngine.GameWord_2;
				case 3 -> playerTwoLeftLifeBi = imageEngine.GameWord_3;
				case 4 -> playerTwoLeftLifeBi = imageEngine.GameWord_4;
				case 5 -> playerTwoLeftLifeBi = imageEngine.GameWord_5;
				case 6 -> playerTwoLeftLifeBi = imageEngine.GameWord_6;
				case 7 -> playerTwoLeftLifeBi = imageEngine.GameWord_7;
				case 8 -> playerTwoLeftLifeBi = imageEngine.GameWord_8;
				case 9 -> playerTwoLeftLifeBi = imageEngine.GameWord_9;
				default -> throw new IllegalStateException("Unexpected value: " + playerTwoLeftLife % 10);
			}
			playerTwoLeftLifeBi = ImageUtil.adapt(playerTwoLeftLifeBi, new Color(117, 117, 117), 9, 8, AdaptType.FitCenter);
			sidebarGraphics.drawImage(playerTwoLeftLifeBi, 16, 168, null);
		}

		//8 200
		StringBuilder levelStrBuilder = new StringBuilder(Integer.toString(level));
		while (levelStrBuilder.length() < 2) {
			levelStrBuilder.insert(0, "0");
		}
		String levelStr = levelStrBuilder.toString();

		int levelOffset = 0;
		while (levelStr.length() != 0) {
			BufferedImage levelBitBi;
			switch (levelStr.charAt(0)) {
				case '0' -> levelBitBi = imageEngine.GameWord_0;
				case '1' -> levelBitBi = imageEngine.GameWord_1;
				case '2' -> levelBitBi = imageEngine.GameWord_2;
				case '3' -> levelBitBi = imageEngine.GameWord_3;
				case '4' -> levelBitBi = imageEngine.GameWord_4;
				case '5' -> levelBitBi = imageEngine.GameWord_5;
				case '6' -> levelBitBi = imageEngine.GameWord_6;
				case '7' -> levelBitBi = imageEngine.GameWord_7;
				case '8' -> levelBitBi = imageEngine.GameWord_8;
				case '9' -> levelBitBi = imageEngine.GameWord_9;
				default -> throw new IllegalStateException("Unexpected value: " + levelStr.charAt(0));
			}

			levelBitBi = ImageUtil.adapt(levelBitBi, new Color(117, 117, 117), 8, 8, AdaptType.FitCenter);
			sidebarGraphics.drawImage(levelBitBi, 8 + levelOffset, 200, null);

			levelStr = levelStr.substring(1);
			levelOffset += 8;
		}

		sidebarImage = ImageUtil.scale(sidebarImage, 2);
		sidebarImage = ImageUtil.adapt(sidebarImage, new Color(117, 117, 117), 70, 416, AdaptType.FitCenter);

		mapGraphics.drawImage(sidebarImage, 416, 0, null);
		return mapImage;
	}

	public synchronized void recyclePanel() {
		for (Bullet bullet : bulletList) {
			bullet.tickThread.interrupt();
		}
		for (Tank tank : tankList) {
			tank.tickThread.interrupt();
		}
		for (BotTank botTank : botTankList) {
			botTank.tickThread.interrupt();
		}
		player1 = null;
		player2 = null;
		map = null;
		closeThreads();
	}
}
