package org.jwybw.battlecity.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Locale;

public class ImageStringBuilder {
	private final ImageEngine imageEngine;
	public String string;
	public Color backcolor = new Color(0, 0, 0, 0);
	public Color fontcolor = Color.BLACK;
	//字符的实际边长（自动转化为正方形）
	public int charSideLength = -1;
	//边框的边长
	public int frameSideLength = -1;
	//字间距
	public int charGap = 1;
	//比例
	public double scale = 1;


	public ImageStringBuilder(String str) {
		imageEngine = new ImageEngine(EngineType.WORD, 1);
		string = str;
	}

	public ImageStringBuilder setScale(float scale) {
		this.scale = scale;
		return this;
	}

	public ImageStringBuilder setString(String string) {
		this.string = string;
		return this;
	}

	public ImageStringBuilder setBackcolor(Color color) {
		this.backcolor = color;
		return this;
	}

	public ImageStringBuilder setFontcolor(Color color) {
		this.fontcolor = color;
		return this;
	}

	public ImageStringBuilder setCharSideLength(int length) {
		this.charSideLength = length;
		return this;
	}

	public ImageStringBuilder setFrameSideLength(int length) {
		this.frameSideLength = length;
		return this;
	}

	public ImageStringBuilder setCharGap(int gap) {
		this.charGap = gap;
		return this;
	}

	public BufferedImage toImage() {
		String tmpStr = string.toUpperCase(Locale.ROOT);
		BufferedImage resBi = new BufferedImage(1, 7, BufferedImage.TYPE_INT_ARGB);
		while (tmpStr.length() > 0) {
			BufferedImage nowBi;
			switch (tmpStr.charAt(0)) {
				case 'A' -> nowBi = imageEngine.Word_A;
				case 'B' -> nowBi = imageEngine.Word_B;
				case 'C' -> nowBi = imageEngine.Word_C;
				case 'D' -> nowBi = imageEngine.Word_D;
				case 'E' -> nowBi = imageEngine.Word_E;
				case 'F' -> nowBi = imageEngine.Word_F;
				case 'G' -> nowBi = imageEngine.Word_G;
				case 'H' -> nowBi = imageEngine.Word_H;
				case 'I' -> nowBi = imageEngine.Word_I;
				case 'J' -> nowBi = imageEngine.Word_J;
				case 'K' -> nowBi = imageEngine.Word_K;
				case 'L' -> nowBi = imageEngine.Word_L;
				case 'M' -> nowBi = imageEngine.Word_M;
				case 'N' -> nowBi = imageEngine.Word_N;
				case 'O' -> nowBi = imageEngine.Word_O;
				case 'P' -> nowBi = imageEngine.Word_P;
				case 'Q' -> nowBi = imageEngine.Word_Q;
				case 'R' -> nowBi = imageEngine.Word_R;
				case 'S' -> nowBi = imageEngine.Word_S;
				case 'T' -> nowBi = imageEngine.Word_T;
				case 'U' -> nowBi = imageEngine.Word_U;
				case 'V' -> nowBi = imageEngine.Word_V;
				case 'W' -> nowBi = imageEngine.Word_W;
				case 'X' -> nowBi = imageEngine.Word_X;
				case 'Y' -> nowBi = imageEngine.Word_Y;
				case 'Z' -> nowBi = imageEngine.Word_Z;
				case '0' -> nowBi = imageEngine.Word_0;
				case '1' -> nowBi = imageEngine.Word_1;
				case '2' -> nowBi = imageEngine.Word_2;
				case '3' -> nowBi = imageEngine.Word_3;
				case '4' -> nowBi = imageEngine.Word_4;
				case '5' -> nowBi = imageEngine.Word_5;
				case '6' -> nowBi = imageEngine.Word_6;
				case '7' -> nowBi = imageEngine.Word_7;
				case '8' -> nowBi = imageEngine.Word_8;
				case '9' -> nowBi = imageEngine.Word_9;
				case ':' -> nowBi = imageEngine.Word_Colon;
				case '?' -> nowBi = imageEngine.Word_Question;
				case '.' -> nowBi = imageEngine.Word_Dot;
				case ',' -> nowBi = imageEngine.Word_Comma;
				case ' ' -> nowBi = ImageUtil.makePureImage(4, 7, new Color(0, 0, 0, 0));
				default -> nowBi = ImageUtil.makePureImage(6, 7, Color.BLACK);
			}
			if (charSideLength != -1) {
				nowBi = ImageUtil.resize(nowBi, charSideLength, charSideLength);
			}
			if (frameSideLength != -1) {
				nowBi = ImageUtil.adapt(nowBi, backcolor, frameSideLength, frameSideLength, AdaptType.FitCenter);
			}
			BufferedImage bigBi = ImageUtil.makePureImage(resBi.getWidth() + charGap + nowBi.getWidth(), Math.max(resBi.getHeight(), nowBi.getHeight()), backcolor);
			Graphics2D bigGraphics = bigBi.createGraphics();
			bigGraphics.drawImage(resBi, 0, 0, null);
			bigGraphics.drawImage(nowBi, resBi.getWidth() + charGap, 0, null);
			resBi = bigBi;
			tmpStr = tmpStr.substring(1);
		}
		if (scale != 1) {
			resBi = ImageUtil.scale(resBi, (float) scale);
		}
		if (fontcolor != Color.BLACK) {
			for (int i = 0; i < resBi.getWidth(); i++) {
				for (int j = 0; j < resBi.getHeight(); j++) {
					if ((resBi.getRGB(i, j) >> 24 & 0xff) != 0) {
						resBi.setRGB(i, j, fontcolor.getRGB());
					}
				}
			}
		}
		return resBi;
	}
}
