package org.jwybw.battlecity.component.blocks;

import java.awt.image.BufferedImage;

public class IronBlock extends Block {
	public IronBlock(int x, int y) {
		super(x, y);
		setBounds(compImages.Iron);
	}

	@Override
	public BufferedImage getImage() {
		return compImages.Iron;
	}
}
