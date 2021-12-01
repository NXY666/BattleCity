package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.Directions;
import org.jwybw.battlecity.component.blocks.Block;
import org.jwybw.battlecity.frame.GameFrame;
import org.jwybw.battlecity.frame.MapPanel;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class Bullet extends Entity {
	public MapPanel mapPanel;
	public int speed = 5;
	public Directions direction;
	public String owner;
	private int renderFrame = 0;

	public Bullet(int x, int y, Directions direction, MapPanel mapPanel, String owner) {
		super(x, y);
		this.mapPanel = mapPanel;
		this.direction = direction;
		this.owner = owner;
		switch (direction) {
			case Up, Down -> setBounds(28, 4);
			case Left, Right -> setBounds(4, 28);
		}
	}

	public void moveBullet() {
		if (this.direction != null) {
			switch (this.direction) {
				case Up -> y -= speed;
				case Down -> y += speed;
				case Left -> x -= speed;
				case Right -> x += speed;
			}
		}
	}

	public void hitBlocks() {
		List<Block> brickList = mapPanel.brickList;
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < brickList.size(); i++) {
			Block brick = brickList.get(i);
			if (this.hit(brick)) {
				this.kill();
				brick.destroy();
			}
		}
		for (Block iron : mapPanel.ironList) {
			if (this.hit(iron)) {
				this.kill();
			}
		}
		if (this.hit(mapPanel.eagleBlock)) {
			this.kill();
			mapPanel.eagleBlock.destroy();
		}
		if (x < 0 || y < 0 || x >= 416 || y >= 416) {
			this.kill();
		}
	}

	public void hitEntities() {
		List<BotTank> botTankList = mapPanel.botTankList;
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < botTankList.size(); i++) {
			BotTank botTank = botTankList.get(i);
			if (botTank.isAlive() && this.hit(botTank)) {
				if (Objects.equals(this.owner, "player")) {
					//玩家打中机器人
					this.kill();
					botTank.hurt();
				} else if (Objects.equals(this.owner, "bot")) {
					//机器人打中机器人
					this.hide();
				}
			}
		}
		List<Tank> tankList = mapPanel.tankList;
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < tankList.size(); i++) {
			Tank tank = tankList.get(i);
			if (tank.isAlive() && this.hit(tank)) {
				if (Objects.equals(this.owner, "bot")) {
					//机器人打中玩家
					this.kill();
					tank.hurt();
				} else if (Objects.equals(this.owner, "player")) {
					//玩家打中玩家
					this.hide();
				}
			}
		}
		List<Bullet> bulletList = mapPanel.bulletList;
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);
			if (bullet != this && bullet.isAlive() && this.hit(bullet)) {
				bullet.kill();
				this.kill();
			}
		}
	}

	@Override
	public BufferedImage getImage() {
		switch (lifeStage) {
			// 正常态
			case 0:
				switch (direction) {
					case Left -> {
						return compImages.Bullet_left;
					}
					case Up -> {
						return compImages.Bullet_up;
					}
					case Right -> {
						return compImages.Bullet_right;
					}
					case Down -> {
						return compImages.Bullet_down;
					}
					default -> throw new IllegalStateException("Unexpected value: " + direction);
				}
				// 爆炸态
			case 1:
				renderFrame++;
				if (renderFrame < 5) {
					return compImages.Bullet_explode_0;
				} else if (renderFrame < 10) {
					return compImages.Bullet_explode_1;
				} else if (renderFrame < 15) {
					return compImages.Bullet_explode_2;
				} else {
					visible = false;
					return compImages.Bullet_explode_0;
				}
			default:
				throw new IllegalStateException("Unexpected value: " + lifeStage);
		}
	}

	@Override
	public void tick() {
		try {
			while (isAlive()) {
				moveBullet();
				hitBlocks();
				hitEntities();
				Thread.sleep(GameFrame.FRESH);
			}
		} catch (NullPointerException | InterruptedException e) {
			e.printStackTrace();
		}
		lifeStage = 1;
	}
}
