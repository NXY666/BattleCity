package org.jwybw.battlecity.component.blocks;

import java.awt.image.BufferedImage;

public class BrickBlock extends Block {
	boolean isLeft = false, isTop = false;

	public BrickBlock(int x, int y) {
		super(x, y);
		destroyable = true;
		if (x % 16 == 0) {
			isLeft = true;
		}
		if (y % 16 == 0) {
			isTop = true;
		}
		setBounds(compImages.Brick_4_LT);
	}

	@Override
	public BufferedImage getImage() {
		if (isLeft && isTop) {
			return (compImages.Brick_4_LT);
		} else if (!isLeft && isTop) {
			return (compImages.Brick_4_RT);
		} else if (isLeft) {
			return (compImages.Brick_4_LB);
		} else {
			return (compImages.Brick_4_RB);
		}
	}
}
