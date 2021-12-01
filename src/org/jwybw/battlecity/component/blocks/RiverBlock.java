package org.jwybw.battlecity.component.blocks;

import java.awt.image.BufferedImage;

public class RiverBlock extends Block {
	protected int renderFrame = 0;

	public RiverBlock(int x, int y) {
		super(x, y);
		setBounds(compImages.River_16_0);
	}

	//获取图像
	public BufferedImage getImage() {
		renderFrame++;
		if (renderFrame % 80 < 40) {
			return compImages.River_16_0;
		} else {
			return compImages.River_16_1;
		}
	}
}
