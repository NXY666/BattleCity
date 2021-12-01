package org.jwybw.battlecity.component;

import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Component {
	protected static ImageEngine compImages;
	protected int x, y, width, height;

	//图像横坐标 纵坐标 图像的宽 图像的高
	public Component(int x, int y) {
		if (compImages == null) {
			compImages = new ImageEngine(EngineType.GAME, 2);
		}
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public abstract BufferedImage getImage();

	//碰撞箱
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void setBounds(Image image) {
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}

	public void setBounds(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean hit(Rectangle vItem) {
		if (vItem == null) {
			return false;
		} else {
			//getBounds 新建一个矩形类此处使用this.getBounds.intersects表示判断矩形坐标相交.
			return this.getBounds().intersects(vItem);
		}
	}

	public boolean hit(Component vItem) {
		return this.hit(vItem.getBounds());
	}
}
