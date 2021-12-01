package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;
import org.jwybw.battlecity.image.ImageStringBuilder;
import org.jwybw.battlecity.image.ImageUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ChooseFrame extends Frame {
	private final BufferedImage chooseStageImage;
	private final BufferedImage hintImage;
	public int playerCount;
	public int selectedLevel = 1;
	ImageStringBuilder stageNumberBuilder;
	BufferedImage lastLevel, thisLevel, nextLevel;

	public ChooseFrame(FrameContainer frame, int playerCount) {
		super(frame);
		imageEngine = new ImageEngine(EngineType.WORD, 3);
		backImage = ImageUtil.makePureImage(frame.realWidth, frame.realHeight, new Color(51, 51, 51));
		backcolor = new Color(51, 51, 51);
		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> selectedLevel++;
					case KeyEvent.VK_LEFT, KeyEvent.VK_A -> selectedLevel--;
					case KeyEvent.VK_ENTER, KeyEvent.VK_J -> transitToFrame(new StageFrame(frame, playerCount, selectedLevel));
					case KeyEvent.VK_ESCAPE -> transitToFrame(new MenuFrame(frame));
				}
				if (selectedLevel < 1) {
					selectedLevel = 1;
				} else if (selectedLevel > 14) {
					selectedLevel = 14;
				} else {
					try {
						lastLevel = ImageUtil.toBufferedImage(ImageUtil.openImage("images/maps/" + (selectedLevel - 1) + ".png"));
					} catch (IOException err) {
						lastLevel = ImageUtil.makePureImage(416, 416, Color.BLACK);
					}
					try {
						thisLevel = ImageUtil.toBufferedImage(ImageUtil.openImage("images/maps/" + (selectedLevel) + ".png"));
					} catch (IOException err) {
						thisLevel = ImageUtil.makePureImage(416, 416, Color.BLACK);
					}
					try {
						nextLevel = ImageUtil.toBufferedImage(ImageUtil.openImage("images/maps/" + (selectedLevel + 1) + ".png"));
					} catch (IOException err) {
						nextLevel = ImageUtil.makePureImage(416, 416, Color.BLACK);
					}
					stageNumberBuilder.setString("stage " + selectedLevel);
					repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
		animationType = Frame.ANIMATION_CLOSE;

		try {
			lastLevel = ImageUtil.toBufferedImage(ImageUtil.openImage("images/maps/" + (selectedLevel - 1) + ".png"));
		} catch (IOException err) {
			lastLevel = ImageUtil.makePureImage(416, 416, Color.BLACK);
		}
		try {
			thisLevel = ImageUtil.toBufferedImage(ImageUtil.openImage("images/maps/" + (selectedLevel) + ".png"));
		} catch (IOException err) {
			thisLevel = ImageUtil.makePureImage(416, 416, Color.BLACK);
		}
		try {
			nextLevel = ImageUtil.toBufferedImage(ImageUtil.openImage("images/maps/" + (selectedLevel + 1) + ".png"));
		} catch (IOException err) {
			nextLevel = ImageUtil.makePureImage(416, 416, Color.BLACK);
		}

		this.playerCount = playerCount;
		chooseStageImage = new ImageStringBuilder("choose stage:")
			.setCharGap(1)
			.setScale(3)
			.setFontcolor(Color.WHITE)
			.toImage();
		hintImage = new ImageStringBuilder("press left or right to choose stage. press fire to start.")
			.setCharGap(0)
			.setScale(2F)
			.setFontcolor(new Color(153, 153, 153))
			.toImage();
		stageNumberBuilder = new ImageStringBuilder("stage " + selectedLevel)
			.setCharGap(1)
			.setScale(3)
			.setFontcolor(Color.WHITE)
			.setFrameSideLength(8);
	}

	@Override
	protected void onFinish() {
		frame.addKeyListener(keyListener);
	}

	@Override
	public void render(Graphics2D screenGraphics) {
		int bigSide = (int) Math.round(frame.realHeight / 2.5), smallSide = frame.realHeight / 4;

		screenGraphics.drawImage(lastLevel, (frame.realWidth - smallSide) / 2 - bigSide, bigSide - smallSide / 2, smallSide, smallSide, null);
		screenGraphics.drawImage(thisLevel, (frame.realWidth - bigSide) / 2, bigSide / 2, bigSide, bigSide, null);
		screenGraphics.drawImage(nextLevel, (frame.realWidth - smallSide) / 2 + bigSide, bigSide - smallSide / 2, smallSide, smallSide, null);

		BufferedImage levelNumberBi = stageNumberBuilder.toImage();
		screenGraphics.drawImage(levelNumberBi, (frame.realWidth - levelNumberBi.getWidth()) / 2, (int) Math.round(bigSide * 1.7), null);

		screenGraphics.drawImage(chooseStageImage, 20, 20, null);
		screenGraphics.drawImage(hintImage, (frame.realWidth - hintImage.getWidth()) / 2, frame.realHeight - (int) (hintImage.getHeight() * 2.5), null);
	}
}
