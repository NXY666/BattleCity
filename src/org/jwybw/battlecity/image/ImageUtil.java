package org.jwybw.battlecity.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
	static public Image openImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	static public BufferedImage makePureImage(int w, int h, Color color) {
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setColor(color);
		graphics2D.fillRect(0, 0, w, h);
		return bufferedImage;
	}

	static public BufferedImage toBufferedImage(Image image) {
		int width = image.getWidth(null), height = image.getHeight(null);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return bi;
	}

	static public BufferedImage clip(BufferedImage bi, int x, int y, int width, int height) {
		return bi.getSubimage(x, y, width, height);
	}

	static public BufferedImage resize(BufferedImage bi, int width, int height) {
		return toBufferedImage(bi.getScaledInstance(width, height, BufferedImage.SCALE_REPLICATE));
	}

	static public BufferedImage scale(BufferedImage bi, float scale) {
		if (scale == 1f) {
			return bi;
		} else {
			return resize(bi, Math.round(bi.getWidth() * scale), Math.round(bi.getHeight() * scale));
		}
	}

	static public BufferedImage adapt(BufferedImage imageBi, Color backcolor, int frameWidth, int frameHeight, AdaptType adaptType) {
		BufferedImage frameBi = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D frameGraphics = frameBi.createGraphics();
		frameGraphics.setColor(backcolor);
		frameGraphics.fillRect(0, 0, frameWidth, frameHeight);

		int imageWidth = imageBi.getWidth();
		int imageHeight = imageBi.getHeight();

		int bgX, bgY, bgW, bgH;
		double bgS;
		switch (adaptType) {
			case FitScreen -> {
				double sW = (double) imageWidth / frameWidth, sH = (double) imageHeight / frameHeight, maxS = Math.max(sW, sH);
				if (sW > 1 || sH > 1) {
					if (sW >= sH) {
						bgW = frameWidth;
						bgH = (int) (imageHeight / maxS);
						bgX = 0;
						bgY = (frameHeight - bgH) / 2;
					} else {
						bgW = (int) (imageWidth / maxS);
						bgH = frameHeight;
						bgX = (frameWidth - bgW) / 2;
						bgY = 0;
					}
				} else if (sW < 1 || sH < 1) {
					if (sW >= sH) {
						bgW = frameWidth;
						bgH = (int) (imageHeight / maxS);
						bgX = 0;
						bgY = (frameHeight - bgH) / 2;
					} else {
						bgW = (int) (imageWidth / maxS);
						bgH = frameHeight;
						bgX = (frameWidth - bgW) / 2;
						bgY = 0;
					}
				} else {
					bgW = frameWidth;
					bgH = frameHeight;
					bgX = 0;
					bgY = 0;
				}
			}
			case FitCenter -> {
				bgX = (frameWidth - imageWidth) / 2;
				bgY = (frameHeight - imageHeight) / 2;
				bgW = imageWidth;
				bgH = imageHeight;
				// frameGraphics.drawImage(imageBi, bgX, bgY, bgW, bgH, null);
			}
			default -> {
				bgX = 0;
				bgY = 0;
				bgW = imageWidth;
				bgH = imageHeight;
				// frameGraphics.drawImage(imageBi, bgX, bgY, bgW, bgH, null);
			}
		}
		frameGraphics.setRenderingHint(
			RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		frameGraphics.drawImage(imageBi, bgX, bgY, bgW, bgH, null);
		return frameBi;
	}

	/**
	 * 通过原偏移位置绘制新偏移位置的图片
	 *
	 * @param graphics  需绘制图片的画笔
	 * @param targetBi  要放置的图片
	 * @param oldBounds 旧坐标范围
	 */
	public static void paintXY(Graphics graphics, BufferedImage targetBi, Rectangle oldBounds) {
		graphics.drawImage(targetBi, (int) Math.round(oldBounds.getCenterX() - (float) targetBi.getWidth() / 2), (int) Math.round(oldBounds.getCenterY() - (float) targetBi.getHeight() / 2), null);
	}

	public static BufferedImage merge(Image topImage, Image bottomImage) {
		int resWidth = bottomImage.getWidth(null), resHeight = bottomImage.getHeight(null);
		int topWidth = topImage.getWidth(null), topHeight = topImage.getHeight(null);
		BufferedImage image = new BufferedImage(resWidth, resHeight, BufferedImage.TYPE_INT_ARGB);
		image.createGraphics();
		Graphics2D bottomGraphics = image.createGraphics();
		bottomGraphics.drawImage(bottomImage, 0, 0, null);
		bottomGraphics.drawImage(topImage, Math.round((float) resWidth / 2 - (float) topWidth / 2), Math.round((float) resHeight / 2 - (float) topHeight / 2), null);
		return image;
	}
}
