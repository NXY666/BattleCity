package org.jwybw.battlecity.component.blocks;

import java.awt.image.BufferedImage;

public class GravelBlock extends Block {
	public GravelBlock(int x, int y) {
		super(x, y);
		setBounds(compImages.Gravel_16);
	}

	@Override
	public BufferedImage getImage() {
		return compImages.Gravel_16;
	}
}
