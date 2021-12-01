package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class StageFrame extends Frame {
	int playerCount, level;

	public StageFrame(FrameContainer frame, int playerCount, int level) {
		super(frame);
		imageEngine = new ImageEngine(EngineType.WORD, 3);
		backImage = new BufferedImage(200, 27, BufferedImage.TYPE_INT_ARGB);
		backcolor = new Color(117, 117, 117);
		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
		animationType = Frame.ANIMATION_OPEN;
		this.playerCount = playerCount;
		this.level = level;
	}

	@Override
	public void render(Graphics2D screenGraphics) {
		screenGraphics.drawImage(imageEngine.GameWord_STAGE, 0, 0, null);
		int tmpLevel = level, pos = 200 - 21;
		while (tmpLevel != 0) {
			switch (tmpLevel % 10) {
				case 0 -> screenGraphics.drawImage(imageEngine.GameWord_0, pos, 0, null);
				case 1 -> screenGraphics.drawImage(imageEngine.GameWord_1, pos, 0, null);
				case 2 -> screenGraphics.drawImage(imageEngine.GameWord_2, pos, 0, null);
				case 3 -> screenGraphics.drawImage(imageEngine.GameWord_3, pos, 0, null);
				case 4 -> screenGraphics.drawImage(imageEngine.GameWord_4, pos, 0, null);
				case 5 -> screenGraphics.drawImage(imageEngine.GameWord_5, pos, 0, null);
				case 6 -> screenGraphics.drawImage(imageEngine.GameWord_6, pos, 0, null);
				case 7 -> screenGraphics.drawImage(imageEngine.GameWord_7, pos, 0, null);
				case 8 -> screenGraphics.drawImage(imageEngine.GameWord_8, pos, 0, null);
				case 9 -> screenGraphics.drawImage(imageEngine.GameWord_9, pos, 0, null);
			}
			pos -= 23;
			tmpLevel /= 10;
		}
	}

	@Override
	protected void onFinish() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		transitToFrame(new GameFrame(frame, playerCount, level));
	}
}
