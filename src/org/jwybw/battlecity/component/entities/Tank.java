package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.Directions;
import org.jwybw.battlecity.component.blocks.Block;
import org.jwybw.battlecity.frame.GameFrame;
import org.jwybw.battlecity.frame.MapPanel;
import org.jwybw.battlecity.image.AdaptType;
import org.jwybw.battlecity.image.ImageUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class Tank extends Entity {
	public int fireLocationX = 0;
	public int fireLocationY = 0;
	public int speed = 2;
	public int bulletCoolDown = 0;
	public boolean attackStage = true;

	public Bullet tankBullet;
	public Directions direction = Directions.Up;

	protected boolean movable = false;
	protected BufferedImage
		tank_up_1_0, tank_up_1_1,
		tank_down_1_0, tank_down_1_1,
		tank_right_1_0, tank_right_1_1,
		tank_left_1_0, tank_left_1_1;
	protected int renderFrame = 0, renderFrame2 = 0;
	int tankType;
	MapPanel mapPanel;

	public Tank(int x, int y, int type, MapPanel mapPanel) {
		super(x, y);
		this.mapPanel = mapPanel;
		// 0：机器人 1：player1 2：player2
		this.tankType = type;
		switch (tankType) {
			case 0 -> {
				switch (this.getClass().getSimpleName()) {
					default -> {
						tank_up_1_0 = ImageUtil.adapt(compImages.WhiteTank_up_5_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_up_1_1 = ImageUtil.adapt(compImages.WhiteTank_up_5_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_down_1_0 = ImageUtil.adapt(compImages.WhiteTank_down_5_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_down_1_1 = ImageUtil.adapt(compImages.WhiteTank_down_5_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_right_1_0 = ImageUtil.adapt(compImages.WhiteTank_right_5_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_right_1_1 = ImageUtil.adapt(compImages.WhiteTank_right_5_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_left_1_0 = ImageUtil.adapt(compImages.WhiteTank_left_5_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_left_1_1 = ImageUtil.adapt(compImages.WhiteTank_left_5_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
					}
					case "SpeedBot" -> {
						tank_up_1_0 = ImageUtil.adapt(compImages.WhiteTank_up_6_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_up_1_1 = ImageUtil.adapt(compImages.WhiteTank_up_6_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_down_1_0 = ImageUtil.adapt(compImages.WhiteTank_down_6_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_down_1_1 = ImageUtil.adapt(compImages.WhiteTank_down_6_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_right_1_0 = ImageUtil.adapt(compImages.WhiteTank_right_6_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_right_1_1 = ImageUtil.adapt(compImages.WhiteTank_right_6_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_left_1_0 = ImageUtil.adapt(compImages.WhiteTank_left_6_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_left_1_1 = ImageUtil.adapt(compImages.WhiteTank_left_6_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
					}
					case "PowerBot" -> {
						tank_up_1_0 = ImageUtil.adapt(compImages.WhiteTank_up_7_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_up_1_1 = ImageUtil.adapt(compImages.WhiteTank_up_7_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_down_1_0 = ImageUtil.adapt(compImages.WhiteTank_down_7_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_down_1_1 = ImageUtil.adapt(compImages.WhiteTank_down_7_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_right_1_0 = ImageUtil.adapt(compImages.WhiteTank_right_7_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_right_1_1 = ImageUtil.adapt(compImages.WhiteTank_right_7_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_left_1_0 = ImageUtil.adapt(compImages.WhiteTank_left_7_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
						tank_left_1_1 = ImageUtil.adapt(compImages.WhiteTank_left_7_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
					}
					case "ArmorBot" -> {
						//在自己类里面重写了，不用这里的
					}
				}
			}
			case 1 -> {
				tank_up_1_0 = ImageUtil.adapt(compImages.YellowTank_up_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_up_1_1 = ImageUtil.adapt(compImages.YellowTank_up_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_down_1_0 = ImageUtil.adapt(compImages.YellowTank_down_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_down_1_1 = ImageUtil.adapt(compImages.YellowTank_down_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_right_1_0 = ImageUtil.adapt(compImages.YellowTank_right_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_right_1_1 = ImageUtil.adapt(compImages.YellowTank_right_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_left_1_0 = ImageUtil.adapt(compImages.YellowTank_left_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_left_1_1 = ImageUtil.adapt(compImages.YellowTank_left_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
			}
			case 2 -> {
				tank_up_1_0 = ImageUtil.adapt(compImages.GreenTank_up_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_up_1_1 = ImageUtil.adapt(compImages.GreenTank_up_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_down_1_0 = ImageUtil.adapt(compImages.GreenTank_down_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_down_1_1 = ImageUtil.adapt(compImages.GreenTank_down_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_right_1_0 = ImageUtil.adapt(compImages.GreenTank_right_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_right_1_1 = ImageUtil.adapt(compImages.GreenTank_right_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_left_1_0 = ImageUtil.adapt(compImages.GreenTank_left_1_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
				tank_left_1_1 = ImageUtil.adapt(compImages.GreenTank_left_1_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
			}
		}
		invincible = true;
		this.setBounds(32, 32);
		mapPanel.startThread(new TankBulletThread());
	}

	public boolean leftWard() {
		renderFrame++;
		if (direction == Directions.Up && y % 16 > 6) {
			y = y + 16 - y % 16;
		} else {
			y = y - y % 16;
		}
		boolean canMove = isSafe(x - speed, y);
		if (canMove) {
			x -= speed;
		}
		direction = Directions.Left;
		return canMove;
	}

	public boolean rightWard() {
		renderFrame++;
		if (direction == Directions.Up && y % 16 > 6) {
			y = y + 16 - y % 16;
		} else {
			y = y - y % 16;
		}
		boolean canMove = isSafe(x + speed, y);
		if (canMove) {
			x += speed;
		}
		direction = Directions.Right;
		return canMove;
	}

	public boolean upWard() {
		renderFrame++;
		if (direction == Directions.Left && x % 16 > 6) {
			x = x + 16 - x % 16;
		} else {
			x = x - x % 16;
		}
		boolean canMove = isSafe(x, y - speed);
		if (canMove) {
			y -= speed;
		}
		direction = Directions.Up;
		return canMove;
	}

	public boolean downWard() {
		renderFrame++;
		if (direction == Directions.Left && x % 16 > 6) {
			x = x + 16 - x % 16;
		} else {
			x = x - x % 16;
		}
		boolean canMove = isSafe(x, y + speed);
		if (canMove) {
			y += speed;
		}
		direction = Directions.Down;
		return canMove;
	}

	public void tankMove(int direction) {
		if (movable) {
			switch (direction) {
				case KeyEvent.VK_W, KeyEvent.VK_UP -> upWard();
				case KeyEvent.VK_A, KeyEvent.VK_LEFT -> leftWard();
				case KeyEvent.VK_S, KeyEvent.VK_DOWN -> downWard();
				case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> rightWard();
				default -> throw new IllegalStateException("Unexpected value: " + direction);
			}
		}
	}

	public boolean isSafe(int x, int y) {
		Rectangle nextLocation = new Rectangle(x, y, width, height);
		List<Block> brickList = mapPanel.brickList;
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < brickList.size(); i++) {
			Block brick = brickList.get(i);
			if (brick != null && brick.hit(nextLocation)) {
				return false;
			}
		}
		for (Block iron : mapPanel.ironList) {
			if (iron.hit(nextLocation)) {
				return false;
			}
		}
		for (Block river : mapPanel.riverList) {
			if (river.hit(nextLocation)) {
				return false;
			}
		}
		if (mapPanel.eagleBlock.hit(nextLocation)) {
			return false;
		}
		for (BotTank tank : mapPanel.botTankList) {
			if (this == tank) {
				continue;
			}
			if (tank.hit(nextLocation)) {
				return false;
			}
		}
		for (Tank tank : mapPanel.tankList) {
			if (tank != this && tank.hit(nextLocation)) {
				return false;
			}
		}
		boolean temp = true;
		if (x < 0) {
			this.x = 0;
			temp = false;
		}
		if (y < 0) {
			this.y = 0;
			temp = false;
		}
		if (x > 416 - width) {
			this.x = 416 - width;
			temp = false;
		}
		if (y > 416 - width) {
			this.y = 416 - width;
			temp = false;
		}
		return temp;
	}

	public void getFireLocation() {
		switch (direction) {
			case Up -> {
				fireLocationX = x + width / 2 - 14;
				fireLocationY = y;
			}
			case Down -> {
				fireLocationX = x + width / 2 - 14;
				fireLocationY = y + height;
			}
			case Right -> {
				fireLocationX = x + width;
				fireLocationY = y + height / 2 - 14;
			}
			case Left -> {
				fireLocationX = x;
				fireLocationY = y + height / 2 - 14;
			}
		}
	}

	public boolean attack(String owner) {
		if ((tankBullet == null || !tankBullet.isAlive()) && movable && attackStage) {
			this.getFireLocation();
			tankBullet = new Bullet(fireLocationX, fireLocationY, direction, mapPanel, owner);
			attackStage = false;
			mapPanel.bulletList.add(tankBullet);
			return true;
		}
		return false;
	}

	@Override
	public BufferedImage getImage() {
		switch (lifeStage) {
			case 0 -> {
				renderFrame++;
				if (renderFrame < 4) {
					return compImages.Tank_spawn_0;
				} else if (renderFrame < 8) {
					return compImages.Tank_spawn_1;
				} else if (renderFrame < 12) {
					return compImages.Tank_spawn_2;
				} else if (renderFrame < 16) {
					return compImages.Tank_spawn_3;
				} else if (renderFrame < 20) {
					return compImages.Tank_spawn_2;
				} else if (renderFrame < 24) {
					return compImages.Tank_spawn_1;
				} else if (renderFrame < 28) {
					return compImages.Tank_spawn_0;
				} else if (renderFrame < 32) {
					return compImages.Tank_spawn_1;
				} else if (renderFrame < 36) {
					return compImages.Tank_spawn_2;
				} else if (renderFrame < 40) {
					return compImages.Tank_spawn_3;
				} else if (renderFrame < 44) {
					return compImages.Tank_spawn_2;
				} else if (renderFrame < 48) {
					return compImages.Tank_spawn_1;
				} else if (renderFrame < 52) {
					return compImages.Tank_spawn_0;
				} else {
					movable = true;
					// invincible = false;
					lifeStage = 1;
					return compImages.Tank_spawn_0;
				}
			}
			case 1 -> {
				BufferedImage resBi;
				switch (direction) {
					case Left -> {
						if (renderFrame % 16 < 8) {
							resBi = tank_left_1_0;
						} else {
							resBi = tank_left_1_1;
						}
					}
					case Up -> {
						if (renderFrame % 16 < 8) {
							resBi = tank_up_1_0;
						} else {
							resBi = tank_up_1_1;
						}
					}
					case Right -> {
						if (renderFrame % 16 < 8) {
							resBi = tank_right_1_0;
						} else {
							resBi = tank_right_1_1;
						}
					}
					case Down -> {
						if (renderFrame % 16 < 8) {
							resBi = tank_down_1_0;
						} else {
							resBi = tank_down_1_1;
						}
					}
					default -> throw new IllegalStateException("Unexpected value: " + direction);
				}
				if (renderFrame2++ % 4 < 2) {
					return ImageUtil.merge(compImages.Player_tank_protect_0, resBi);
				} else {
					return ImageUtil.merge(compImages.Player_tank_protect_1, resBi);
				}

			}
			case 2 -> {
				switch (direction) {
					case Left -> {
						if (renderFrame % 16 < 8) {
							return tank_left_1_0;
						} else {
							return tank_left_1_1;
						}
					}
					case Up -> {
						if (renderFrame % 16 < 8) {
							return tank_up_1_0;
						} else {
							return tank_up_1_1;
						}
					}
					case Right -> {
						if (renderFrame % 16 < 8) {
							return tank_right_1_0;
						} else {
							return tank_right_1_1;
						}
					}
					case Down -> {
						if (renderFrame % 16 < 8) {
							return tank_down_1_0;
						} else {
							return tank_down_1_1;
						}
					}
					default -> throw new IllegalStateException("Unexpected value: " + direction);
				}
			}
			case 3 -> {
				renderFrame++;
				if (renderFrame < 5) {
					return compImages.Bullet_explode_0;
				} else if (renderFrame < 10) {
					return compImages.Bullet_explode_1;
				} else if (renderFrame < 13) {
					return compImages.Bullet_explode_2;
				} else if (renderFrame < 16) {
					return compImages.Tank_explode_0;
				} else if (renderFrame < 25) {
					return compImages.Tank_explode_1;
				} else if (renderFrame < 30) {
					return compImages.Bullet_explode_2;
				} else {
					visible = false;
					return compImages.Bullet_explode_0;
				}
			}
			default -> throw new IllegalStateException("Unexpected value: " + lifeStage);
		}
	}

	@Override
	public void tick() {
		try {
			while (lifeStage != 1) {
				Thread.sleep(GameFrame.FRESH);
			}
			if (tankType != 0) {
				renderFrame = 0;
				Thread.sleep(3000);
			}
			invincible = false;
			lifeStage = 2;

			//死亡切换判定
			while (health != 0) {
				Thread.sleep(GameFrame.FRESH);
			}
			renderFrame = 0;
			movable = false;
			lifeStage = 3;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void attackCoolDownTick() {
		//子弹冷却时间
		try {
			while (this.health > 0) {
				if (!attackStage) {
					bulletCoolDown += GameFrame.FRESH;
				}
				if (bulletCoolDown >= 320) {
					bulletCoolDown = 0;
					attackStage = true;
				}
				Thread.sleep(GameFrame.FRESH);
			}
		} catch (NullPointerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected class TankBulletThread extends Thread {
		@Override
		public void run() {
			attackCoolDownTick();
		}
	}
}