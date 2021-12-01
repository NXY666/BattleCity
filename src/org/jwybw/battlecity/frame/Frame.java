package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.image.AdaptType;
import org.jwybw.battlecity.image.ImageEngine;
import org.jwybw.battlecity.image.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

abstract public class Frame extends JPanel {
	static public final int ANIMATION_NONE = 0;
	static public final int ANIMATION_CLOSE = 1;
	static public final int ANIMATION_OPEN = 2;

	protected final FrameContainer frame;
	//销毁自己
	public boolean keepAlive = true;
	//必要
	protected ImageEngine imageEngine;
	protected Image backImage;
	protected Color backcolor;
	protected KeyListener keyListener;
	protected Boolean adaptScreen = false;
	protected BufferedImage screenBuffer;
	//切换动画
	protected boolean startTransition = false;
	protected int animationType = 1;
	protected int transitProgress = 1;
	protected int maxTransitProgress = 50;
	protected Frame transitTargetFrame;
	protected BufferedImage transitTargetImage;
	//完成事件
	private boolean hasFinish = false;

	public Frame(FrameContainer frame) {
		this.frame = frame;
		this.setOpaque(false);
	}

	public abstract void render(Graphics2D screenGraphics);

	@Override
	public void paint(Graphics graphics) {
		screenBuffer = ImageUtil.toBufferedImage(backImage);
		Graphics2D screenGraphics = screenBuffer.createGraphics();
		render(screenGraphics);
		setScreen(graphics, screenBuffer);
		setBackColor(backcolor);
		if (startTransition) {
			transitAnimation(graphics);
		}
		if (!hasFinish) {
			hasFinish = true;
			onFinish();
		}
	}

	protected void onFinish() {
	}

	// 将渲染后的图片转换成适应屏幕的背景图，然后给graphics
	protected void setScreen(Graphics graphics, Image image) {
		if (adaptScreen) {
			image = ImageUtil.adapt(ImageUtil.toBufferedImage(image), backcolor, frame.realWidth, frame.realHeight, AdaptType.FitScreen);
		} else {
			image = ImageUtil.adapt(ImageUtil.toBufferedImage(image), backcolor, frame.realWidth, frame.realHeight, AdaptType.FitCenter);
		}
		graphics.drawImage(image, 0, 0, null);
	}

	protected void setBackColor(Color color) {
		frame.getContentPane().setBackground(color);
	}

	//切换动画
	protected void transitToFrame(Frame targetFrame) {
		frame.removeKeyListener(keyListener);
		transitTargetFrame = targetFrame;

		transitTargetImage = new BufferedImage(frame.realWidth, frame.realHeight, BufferedImage.TYPE_INT_ARGB);
		BufferedImage tmpBuffer = ImageUtil.toBufferedImage(targetFrame.backImage);
		targetFrame.render(tmpBuffer.createGraphics());
		targetFrame.setScreen(transitTargetImage.createGraphics(), tmpBuffer);

		new ShiftTimer(10).start();

		startTransition = true;
	}

	protected void transitAnimation(Graphics graphics) {
		int frameWidth = getWidth(), frameHeight = getHeight(), maxHeight = getHeight() / 2;
		double addPerFrame = (double) maxHeight / maxTransitProgress;
		int nowHeight = (int) Math.round(addPerFrame * transitProgress);
		if (nowHeight > maxHeight) {
			graphics.drawImage(transitTargetImage, 0, 0, null);
			return;
		}
		switch (animationType) {
			case ANIMATION_NONE -> {
				transitProgress = maxTransitProgress;
			}
			case ANIMATION_CLOSE -> {
				Image topImg = transitTargetImage.getSubimage(0, 0, frameWidth, nowHeight);
				Image bottomImg = transitTargetImage.getSubimage(0, frameHeight - nowHeight, frameWidth, nowHeight);

				graphics.drawImage(topImg, 0, 0, null);
				graphics.drawImage(bottomImg, 0, frameHeight - nowHeight, null);
			}
			case ANIMATION_OPEN -> {
				Image topImg = transitTargetImage.getSubimage(0, maxHeight - nowHeight, frameWidth, nowHeight);
				Image bottomImg = transitTargetImage.getSubimage(0, maxHeight, frameWidth, nowHeight);

				graphics.drawImage(topImg, 0, maxHeight - nowHeight, null);
				graphics.drawImage(bottomImg, 0, maxHeight, null);
			}
		}
	}

	private class ShiftTimer extends Thread {
		private final int interval;

		public ShiftTimer(int interval) {
			this.interval = interval;
		}

		public void run() {
			try {
				while (keepAlive) {
					Thread.sleep(interval);
					repaint();
					if (transitProgress++ > maxTransitProgress) {
						frame.setPanel(transitTargetFrame);
						keepAlive = false;
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

