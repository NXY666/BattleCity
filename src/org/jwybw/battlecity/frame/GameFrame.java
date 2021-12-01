package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.component.entities.*;
import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;
import org.jwybw.battlecity.image.ImageUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class GameFrame extends Frame {
	public static final int FRESH = 16;

	private final Random random = new Random();
	private final Rectangle respawnSpace1 = new Rectangle(0, 0, 48, 48);
	private final Rectangle respawnSpace2 = new Rectangle(176, 0, 64, 64);
	private final Rectangle respawnSpace3 = new Rectangle(368, 0, 48, 48);
	private final int[] spawnPoint1 = {0, 0};
	private final int[] spawnPoint2 = {192, 0};
	private final int[] spawnPoint3 = {384, 0};

	public int level, playerCount, gameState = 0;
	public MapPanel mapPanel;
	public Stack<Integer> playerOneKeyQueue = new Stack<>(), playerTwoKeyQueue = new Stack<>();
	public Map<Integer, Boolean> playerOneKeyState = new HashMap<>(), playerTwoKeyState = new HashMap<>();
	public int playerOneNowKey = -1, playerTwoNowKey = -1;
	private int renderFrame = 0;
	private long lastSpawnBotTime = System.currentTimeMillis();

	public GameFrame(FrameContainer frame, int playerCount, int level) {
		super(frame);
		imageEngine = new ImageEngine(EngineType.WORD, 1);
		backImage = ImageUtil.makePureImage(416 + 70 + 25, 416 + 50, new Color(117, 117, 117));
		backcolor = new Color(117, 117, 117);
		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int pressedKey = e.getKeyCode();
				if (pressedKey == KeyEvent.VK_ESCAPE) {
					transitToFrame(new MenuFrame(frame));
					return;
				}
				if (mapPanel.player1 != null && mapPanel.player1.isAlive()) {
					switch (pressedKey) {
						case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D -> {
							playerOneNowKey = pressedKey;
							if (!playerOneKeyState.getOrDefault(pressedKey, false)) {
								playerOneKeyState.put(pressedKey, true);
								playerOneKeyQueue.push(pressedKey);
							}
						}
						case KeyEvent.VK_J -> {
							playerOneKeyState.put(pressedKey, true);
							mapPanel.player1.attack("player");
						}
					}
				}
				if (mapPanel.player2 != null && mapPanel.player2.isAlive()) {
					switch (pressedKey) {
						case KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT -> {
							playerTwoNowKey = pressedKey;
							if (!playerTwoKeyState.getOrDefault(pressedKey, false)) {
								playerTwoKeyState.put(pressedKey, true);
								playerTwoKeyQueue.push(pressedKey);
							}
						}
						case KeyEvent.VK_SLASH -> {
							playerTwoKeyState.put(pressedKey, true);
							mapPanel.player2.attack("player");
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int releasedKey = e.getKeyCode();
				if (mapPanel.player1 != null && mapPanel.player1.isAlive()) {
					switch (releasedKey) {
						case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D -> {
							playerOneKeyState.put(releasedKey, false);
							playerOneNowKey = -1;
							while (!playerOneKeyQueue.isEmpty()) {
								int preKey = playerOneKeyQueue.peek();
								if (playerOneKeyState.getOrDefault(preKey, false)) {
									playerOneNowKey = preKey;
									break;
								} else {
									playerOneKeyQueue.pop();
								}
							}
						}
						case KeyEvent.VK_J -> playerOneKeyState.put(releasedKey, false);
					}
				}
				if (mapPanel.player2 != null && mapPanel.player2.isAlive()) {
					switch (releasedKey) {
						case KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT -> {
							playerTwoKeyState.put(releasedKey, false);
							playerTwoNowKey = -1;
							while (!playerTwoKeyQueue.isEmpty()) {
								int preKey = playerTwoKeyQueue.peek();
								if (playerTwoKeyState.getOrDefault(preKey, false)) {
									playerTwoNowKey = preKey;
									break;
								} else {
									playerTwoKeyQueue.pop();
								}
							}
						}
						case KeyEvent.VK_SLASH -> playerTwoKeyState.put(releasedKey, false);
					}
				}
			}
		};
		this.level = level;
		this.playerCount = playerCount;
		adaptScreen = true;
		mapPanel = new MapPanel(level);

		if (playerCount >= 1) {
			mapPanel.player1 = new Tank(128, 384, 1, mapPanel);
			mapPanel.tankList.add(mapPanel.player1);
		}
		if (playerCount >= 2) {
			mapPanel.player2 = new Tank(256, 384, 2, mapPanel);
			mapPanel.tankList.add(mapPanel.player2);
		}
	}

	@Override
	protected void onFinish() {
		frame.addKeyListener(keyListener);
		new RefreshGame().start();
		new TickGame().start();
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(mapPanel.toImage(), 25, 25, null);
		if (gameState == 1) {
			renderFrame++;
			if (renderFrame > 200) {
				gameState = 3;
				if (level == 14) {
					transitToFrame(new MenuFrame(this.frame));
				} else {
					transitToFrame(new StageFrame(this.frame, playerCount, mapPanel.level + 1));
				}
			}
		} else if (gameState == 2) {
			renderFrame++;
			if (renderFrame < 100) {
				BufferedImage gameOverImg = ImageUtil.scale(imageEngine.GameWord_GAMEOVER, 2);
				int width = backImage.getWidth(null), height = backImage.getHeight(null);
				int overWidth = gameOverImg.getWidth(), overHeight = gameOverImg.getHeight();

				double yPerFrame = ((double) height - (double) (height - overHeight) / 2) / 100;

				graphics.drawImage(gameOverImg, (width - overWidth) / 2, (int) Math.round(height - renderFrame * yPerFrame), null);
			} else if (renderFrame < 200) {
				BufferedImage gameOverImg = ImageUtil.scale(imageEngine.GameWord_GAMEOVER, 2);
				int width = backImage.getWidth(null), height = backImage.getHeight(null);
				int overWidth = gameOverImg.getWidth(), overHeight = gameOverImg.getHeight();
				graphics.drawImage(gameOverImg, (width - overWidth) / 2, (height - overHeight) / 2, null);
			} else {
				gameState = 3;
				transitToFrame(new MenuFrame(frame));
			}
		}
	}

	@SuppressWarnings("fallthrough")
	public void spawnBot() {
		//TankBot
		if (System.currentTimeMillis() - lastSpawnBotTime < 100 ||
			mapPanel.botTankList.size() >= playerCount * 2 ||
			mapPanel.map.unusedBot == 0) {
			return;
		} else {
			lastSpawnBotTime = System.currentTimeMillis();
		}
		Rectangle nowSpawnSpace;
		int[] nowSpawnPoint;
		switch (random.nextInt(3)) {
			case 0 -> {
				nowSpawnSpace = respawnSpace1;
				nowSpawnPoint = spawnPoint1;
			}
			case 1 -> {
				nowSpawnSpace = respawnSpace2;
				nowSpawnPoint = spawnPoint2;
			}
			case 2 -> {
				nowSpawnSpace = respawnSpace3;
				nowSpawnPoint = spawnPoint3;
			}
			default -> throw new IllegalStateException("Unexpected value to choose spawn point.");
		}
		for (BotTank botTank : mapPanel.botTankList) {
			if (botTank.hit(nowSpawnSpace)) {
				return;
			}
		}
		for (Tank tank : mapPanel.tankList) {
			if (tank.hit(nowSpawnSpace)) {
				return;
			}
		}
		BotTank botTank;
		switch (random.nextInt(4)) {
			case 0: {
				if (this.mapPanel.map.unusedArmorBot >= 0) {
					botTank = new ArmorBot(nowSpawnPoint[0], nowSpawnPoint[1], mapPanel);
					this.mapPanel.map.unusedArmorBot--;
					break;
				}
			}
			case 1: {
				if (this.mapPanel.map.unusedPowerBotBot > 0) {
					botTank = new PowerBot(nowSpawnPoint[0], nowSpawnPoint[1], mapPanel);
					this.mapPanel.map.unusedPowerBotBot--;
					break;
				}
			}
			case 2: {
				if (this.mapPanel.map.unusedFastBot > 0) {
					botTank = new SpeedBot(nowSpawnPoint[0], nowSpawnPoint[1], mapPanel);
					this.mapPanel.map.unusedFastBot--;
					break;
				}
			}
			case 3: {
				if (this.mapPanel.map.unusedBasicBot > 0) {
					botTank = new BotTank(nowSpawnPoint[0], nowSpawnPoint[1], mapPanel);
					this.mapPanel.map.unusedBasicBot--;
					break;
				}
			}
			default:
				return;
		}
		mapPanel.botTankList.add(botTank);
		mapPanel.map.unusedBot--;
	}

	public void tick() {
		spawnBot();

		if (mapPanel.playerOneLeftLife > 0 && !mapPanel.player1.isVisible()) {
			mapPanel.tankList.remove(mapPanel.player1);
			playerOneNowKey = -1;
			playerOneKeyQueue.clear();
			playerOneKeyState.clear();
			mapPanel.player1 = new Tank(128, 384, 1, mapPanel);
			mapPanel.tankList.add(mapPanel.player1);
			mapPanel.playerOneLeftLife--;
		}
		if (mapPanel.player2 != null && mapPanel.playerTwoLeftLife > 0 && !mapPanel.player2.isVisible()) {
			mapPanel.tankList.remove(mapPanel.player2);
			playerTwoNowKey = -1;
			playerTwoKeyQueue.clear();
			playerTwoKeyState.clear();
			mapPanel.player2 = new Tank(256, 384, 2, mapPanel);
			mapPanel.tankList.add(mapPanel.player2);
			mapPanel.playerTwoLeftLife--;
		}

		if (gameState == 0) {
			if (!mapPanel.eagleBlock.isExist() ||
				(playerCount < 1 || mapPanel.playerOneLeftLife == 0 && !mapPanel.player1.isVisible()) &&
					(playerCount < 2 || mapPanel.playerTwoLeftLife == 0 && !mapPanel.player2.isVisible())) {
				//失败
				gameState = 2;
				renderFrame = 0;
			} else if (mapPanel.botTankList.size() == 0 && mapPanel.map.unusedBot == 0) {
				//成功
				gameState = 1;
				renderFrame = 0;
			}
		}

		//player1控制
		if (playerOneNowKey != -1) {
			mapPanel.player1.tankMove(playerOneNowKey);
		}
		if (playerOneKeyState.getOrDefault(KeyEvent.VK_J, false)) {
			mapPanel.player1.attack("player");
		}
		//player2控制
		if (playerTwoNowKey != -1) {
			mapPanel.player2.tankMove(playerTwoNowKey);
		}
		if (playerTwoKeyState.getOrDefault(KeyEvent.VK_SLASH, false)) {
			mapPanel.player2.attack("player");
		}
	}

	public class TickGame extends Thread {
		public void run() {
			while (keepAlive) {
				tick();
				try {
					Thread.sleep(FRESH);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mapPanel.recyclePanel();
		}
	}

	public class RefreshGame extends Thread {
		public void run() {
			while (keepAlive) {
				repaint();
				try {
					Thread.sleep(FRESH);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
