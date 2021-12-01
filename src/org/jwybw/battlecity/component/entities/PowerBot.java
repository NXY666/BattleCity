package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.frame.MapPanel;

public class PowerBot extends BotTank {
	public PowerBot(int x, int y, MapPanel mapPanel) {
		super(x, y, mapPanel);
		this.health = 1;
		this.speed = 1;
		this.tankType = 0;
		this.simpleAttack = 40;
		this.preciseAttack = 50;
	}
}
