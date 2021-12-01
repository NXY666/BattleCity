package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.frame.MapPanel;
import org.jwybw.battlecity.image.AdaptType;
import org.jwybw.battlecity.image.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ArmorBot extends BotTank {
	protected BufferedImage
		tank_up_1_0, tank_up_1_1, tank_up_2_0, tank_up_2_1, tank_up_3_0, tank_up_3_1,
		tank_down_1_0, tank_down_1_1, tank_down_2_0, tank_down_2_1, tank_down_3_0, tank_down_3_1,
		tank_right_1_0, tank_right_1_1, tank_right_2_0, tank_right_2_1, tank_right_3_0, tank_right_3_1,
		tank_left_1_0, tank_left_1_1, tank_left_2_0, tank_left_2_1, tank_left_3_0, tank_left_3_1;
	int renderFrame3 = 0;

	public ArmorBot(int x, int y, MapPanel mapPanel) {
		super(x, y, mapPanel);
		this.health = new Random().nextInt(3) + 2;
		this.tankType = 0;
		//白的
		tank_up_1_0 = ImageUtil.adapt(compImages.WhiteTank_up_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_up_1_1 = ImageUtil.adapt(compImages.WhiteTank_up_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_down_1_0 = ImageUtil.adapt(compImages.WhiteTank_down_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_down_1_1 = ImageUtil.adapt(compImages.WhiteTank_down_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_right_1_0 = ImageUtil.adapt(compImages.WhiteTank_right_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_right_1_1 = ImageUtil.adapt(compImages.WhiteTank_right_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_left_1_0 = ImageUtil.adapt(compImages.WhiteTank_left_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_left_1_1 = ImageUtil.adapt(compImages.WhiteTank_left_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		//黄的
		tank_up_2_0 = ImageUtil.adapt(compImages.YellowTank_up_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_up_2_1 = ImageUtil.adapt(compImages.YellowTank_up_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_down_2_0 = ImageUtil.adapt(compImages.YellowTank_down_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_down_2_1 = ImageUtil.adapt(compImages.YellowTank_down_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_right_2_0 = ImageUtil.adapt(compImages.YellowTank_right_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_right_2_1 = ImageUtil.adapt(compImages.YellowTank_right_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_left_2_0 = ImageUtil.adapt(compImages.YellowTank_left_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_left_2_1 = ImageUtil.adapt(compImages.YellowTank_left_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		//绿的
		tank_up_3_0 = ImageUtil.adapt(compImages.GreenTank_up_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_up_3_1 = ImageUtil.adapt(compImages.GreenTank_up_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_down_3_0 = ImageUtil.adapt(compImages.GreenTank_down_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_down_3_1 = ImageUtil.adapt(compImages.GreenTank_down_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_right_3_0 = ImageUtil.adapt(compImages.GreenTank_right_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_right_3_1 = ImageUtil.adapt(compImages.GreenTank_right_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_left_3_0 = ImageUtil.adapt(compImages.GreenTank_left_8_0, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
		tank_left_3_1 = ImageUtil.adapt(compImages.GreenTank_left_8_1, new Color(0, 0, 0, 0), 32, 32, AdaptType.FitCenter);
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
				renderFrame3++;
				BufferedImage
					frame_0_left_0, frame_0_left_1,
					frame_0_up_0, frame_0_up_1,
					frame_0_right_0, frame_0_right_1,
					frame_0_down_0, frame_0_down_1,
					frame_1_left_0, frame_1_left_1,
					frame_1_up_0, frame_1_up_1,
					frame_1_right_0, frame_1_right_1,
					frame_1_down_0, frame_1_down_1;

				switch (health) {
					default -> {
						frame_0_left_0 = tank_left_1_0;
						frame_0_left_1 = tank_left_1_1;
						frame_0_up_0 = tank_up_1_0;
						frame_0_up_1 = tank_up_1_1;
						frame_0_right_0 = tank_right_1_0;
						frame_0_right_1 = tank_right_1_1;
						frame_0_down_0 = tank_down_1_0;
						frame_0_down_1 = tank_down_1_1;

						frame_1_left_0 = tank_left_1_0;
						frame_1_left_1 = tank_left_1_1;
						frame_1_up_0 = tank_up_1_0;
						frame_1_up_1 = tank_up_1_1;
						frame_1_right_0 = tank_right_1_0;
						frame_1_right_1 = tank_right_1_1;
						frame_1_down_0 = tank_down_1_0;
						frame_1_down_1 = tank_down_1_1;
					}
					case 2 -> {
						frame_0_left_0 = tank_left_1_0;
						frame_0_left_1 = tank_left_1_1;
						frame_0_up_0 = tank_up_1_0;
						frame_0_up_1 = tank_up_1_1;
						frame_0_right_0 = tank_right_1_0;
						frame_0_right_1 = tank_right_1_1;
						frame_0_down_0 = tank_down_1_0;
						frame_0_down_1 = tank_down_1_1;

						frame_1_left_0 = tank_left_2_0;
						frame_1_left_1 = tank_left_2_1;
						frame_1_up_0 = tank_up_2_0;
						frame_1_up_1 = tank_up_2_1;
						frame_1_right_0 = tank_right_2_0;
						frame_1_right_1 = tank_right_2_1;
						frame_1_down_0 = tank_down_2_0;
						frame_1_down_1 = tank_down_2_1;
					}
					case 3 -> {
						frame_0_left_0 = tank_left_1_0;
						frame_0_left_1 = tank_left_1_1;
						frame_0_up_0 = tank_up_1_0;
						frame_0_up_1 = tank_up_1_1;
						frame_0_right_0 = tank_right_1_0;
						frame_0_right_1 = tank_right_1_1;
						frame_0_down_0 = tank_down_1_0;
						frame_0_down_1 = tank_down_1_1;

						frame_1_left_0 = tank_left_3_0;
						frame_1_left_1 = tank_left_3_1;
						frame_1_up_0 = tank_up_3_0;
						frame_1_up_1 = tank_up_3_1;
						frame_1_right_0 = tank_right_3_0;
						frame_1_right_1 = tank_right_3_1;
						frame_1_down_0 = tank_down_3_0;
						frame_1_down_1 = tank_down_3_1;
					}
					case 4 -> {
						frame_0_left_0 = tank_left_2_0;
						frame_0_left_1 = tank_left_2_1;
						frame_0_up_0 = tank_up_2_0;
						frame_0_up_1 = tank_up_2_1;
						frame_0_right_0 = tank_right_2_0;
						frame_0_right_1 = tank_right_2_1;
						frame_0_down_0 = tank_down_2_0;
						frame_0_down_1 = tank_down_2_1;

						frame_1_left_0 = tank_left_3_0;
						frame_1_left_1 = tank_left_3_1;
						frame_1_up_0 = tank_up_3_0;
						frame_1_up_1 = tank_up_3_1;
						frame_1_right_0 = tank_right_3_0;
						frame_1_right_1 = tank_right_3_1;
						frame_1_down_0 = tank_down_3_0;
						frame_1_down_1 = tank_down_3_1;
					}
				}
				switch (direction) {
					case Left -> {
						if (renderFrame % 16 < 8) {
							if (renderFrame3 % 4 < 2) {
								return frame_0_left_0;
							} else {
								return frame_1_left_0;
							}
						} else {
							if (renderFrame3 % 4 < 2) {
								return frame_0_left_1;
							} else {
								return frame_1_left_1;
							}
						}
					}
					case Up -> {
						if (renderFrame % 16 < 8) {
							if (renderFrame3 % 4 < 2) {
								return frame_0_up_0;
							} else {
								return frame_1_up_0;
							}
						} else {
							if (renderFrame3 % 4 < 2) {
								return frame_0_up_1;
							} else {
								return frame_1_up_1;
							}
						}
					}
					case Right -> {
						if (renderFrame % 16 < 8) {
							if (renderFrame3 % 4 < 2) {
								return frame_0_right_0;
							} else {
								return frame_1_right_0;
							}
						} else {
							if (renderFrame3 % 4 < 2) {
								return frame_0_right_1;
							} else {
								return frame_1_right_1;
							}
						}
					}
					case Down -> {
						if (renderFrame % 16 < 8) {
							if (renderFrame3 % 4 < 2) {
								return frame_0_down_0;
							} else {
								return frame_1_down_0;
							}
						} else {
							if (renderFrame3 % 4 < 2) {
								return frame_0_down_1;
							} else {
								return frame_1_down_1;
							}
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
}
