package org.jwybw.battlecity.component.blocks;

import java.awt.image.BufferedImage;

public class GrassBlock extends Block {
	public GrassBlock(int x, int y) {
		super(x, y);
		this.setBounds(compImages.Grass_16);
	}

	@Override
	public BufferedImage getImage() {
		return compImages.Grass_16;
	}
}
