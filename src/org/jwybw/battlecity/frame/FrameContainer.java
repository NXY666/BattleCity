package org.jwybw.battlecity.frame;

import org.jwybw.battlecity.image.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FrameContainer extends JFrame {
	protected int realWidth, realHeight;

	public FrameContainer() {
		setTitle("BattleCity");
		setSize(750, 700);
		try {
			setIconImage(ImageUtil.scale(ImageIO.read(new File("./images/favicon.png")), 4));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getContentPane().setBackground(Color.BLACK);
		setResizable(false);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPanel(new StartFrame(this));
	}

	public void setPanel(JPanel panel) {
		Container c = getContentPane();
		c.removeAll();
		c.add(panel);
		c.validate();
	}
}
