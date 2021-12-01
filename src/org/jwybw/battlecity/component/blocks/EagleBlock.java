package org.jwybw.battlecity.component.blocks;

import java.awt.image.BufferedImage;

public class EagleBlock extends Block {
	public EagleBlock(int x, int y) {
		super(x, y);
		destroyable = true;
		setBounds(compImages.Base_alive);
	}

	@Override
	public BufferedImage getImage() {
		if (exist) {
			return compImages.Base_alive;
		} else {
			return compImages.Base_dead;
		}
	}
}
