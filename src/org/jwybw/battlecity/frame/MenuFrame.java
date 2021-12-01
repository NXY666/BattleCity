package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.image.EngineType;
import org.jwybw.battlecity.image.ImageEngine;
import org.jwybw.battlecity.image.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuFrame extends Frame {
	public int chooseType = 1;

	public MenuFrame(FrameContainer frame) {
		super(frame);
		imageEngine = new ImageEngine(EngineType.MENU, 1);
		backImage = imageEngine.Menu_Background;
		backcolor = Color.BLACK;
		adaptScreen = true;
		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						System.out.println("上");
						if (--chooseType < 1) {
							chooseType = 3;
						}
						break;
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						System.out.println("下");
						if (++chooseType > 3) {
							chooseType = 1;
						}
						break;
					case KeyEvent.VK_ENTER:
					case KeyEvent.VK_J:
						if (chooseType == 1 || chooseType == 2) {
							transitToFrame(new ChooseFrame(frame, chooseType));
						} else {
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setDialogTitle("导出地图图片");
							fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							fileChooser.setCurrentDirectory(new File("D:/"));
							int option = fileChooser.showOpenDialog(frame);
							if (option == JFileChooser.APPROVE_OPTION) {
								File file = fileChooser.getSelectedFile();
								for (int i = 1; i <= 14; i++) {
									MapPanel mapPanel = new MapPanel(i);
									BufferedImage bufferedImage = mapPanel.toImage();
									try {
										ImageIO.write(ImageUtil.clip(bufferedImage, 0, 0, 416, 416), "png", new File(file + "/" + i + ".png"));
									} catch (IOException ex) {
										ex.printStackTrace();
									}
								}
								JOptionPane.showMessageDialog(frame, "地图图片导出操作已完成！", "操作完成", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(frame, "地图图片导出操作已取消。", "操作取消", JOptionPane.WARNING_MESSAGE);
							}
						}
						return;
					default:
						return;
				}
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
	}

	@Override
	public void render(Graphics2D screenGraphics) {
		if (chooseType == 1) {
			screenGraphics.drawImage(imageEngine.Menu_Tank, 65, 125, null);
		} else {
			screenGraphics.drawImage(imageEngine.Menu_Black, 65, 125, null);
		}
		if (chooseType == 2) {
			screenGraphics.drawImage(imageEngine.Menu_Tank, 65, 141, null);
		} else {
			screenGraphics.drawImage(imageEngine.Menu_Black, 65, 141, null);
		}
		if (chooseType == 3) {
			screenGraphics.drawImage(imageEngine.Menu_Tank, 65, 157, null);
		} else {
			screenGraphics.drawImage(imageEngine.Menu_Black, 65, 157, null);
		}
	}

	@Override
	protected void onFinish() {
		frame.addKeyListener(keyListener);
	}
}
