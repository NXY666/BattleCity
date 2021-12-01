package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;

import java.awt.*;

public class StartFrame extends Frame {
	public StartFrame(FrameContainer frame) {
		super(frame);
		imageEngine = new ImageEngine(EngineType.MENU, 1);
		backImage = imageEngine.Menu_Black;
		backcolor = Color.BLACK;
	}

	@Override
	public void render(Graphics2D graphics2D) {
		frame.realWidth = getWidth();
		frame.realHeight = getHeight();
	}

	@Override
	protected void onFinish() {
		transitToFrame(new MenuFrame(frame));
	}
}
