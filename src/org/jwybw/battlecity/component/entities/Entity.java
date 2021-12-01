package org.jwybw.battlecity.component.entities;

import org.jwybw.battlecity.component.Component;

public abstract class Entity extends Component {
	public Thread tickThread;
	protected boolean visible = true;
	protected volatile int health = 1;
	protected boolean invincible = false;
	protected volatile int lifeStage = 0;

	public Entity(int x, int y) {
		super(x, y);
		tickThread = new TickThread();
		tickThread.start();
	}

	public boolean isAlive() {
		return health != 0;
	}

	public boolean isVisible() {
		return visible;
	}

	public synchronized void hurt() {
		if (!invincible) {
			health--;
		}
	}

	public void kill() {
		if (!invincible) {
			health = 0;
		}
	}

	public void hide() {
		health = 0;
		visible = false;
	}

	/**
	 * 使用多线程管理该实体
	 */
	public void tick() {
	}

	protected class TickThread extends Thread {
		@Override
		public void run() {
			tick();
		}
	}
}
