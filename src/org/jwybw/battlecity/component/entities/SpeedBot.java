package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.frame.MapPanel;

public class SpeedBot extends BotTank {
	public SpeedBot(int x, int y, MapPanel mapPanel) {
		super(x, y, mapPanel);
		this.speed = 2;
		this.tankType = 0;
	}
}
