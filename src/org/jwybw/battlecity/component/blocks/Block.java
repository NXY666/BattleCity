package org.jwybw.battlecity.component.blocks;

import org.jwybw.battlecity.component.Component;

public abstract class Block extends Component {
	protected boolean exist = true;
	protected boolean destroyable = false;

	public Block(int x, int y) {
		super(x, y);
	}

	public boolean isExist() {
		return exist;
	}

	public void destroy() {
		if (destroyable) {
			exist = false;
		}
	}
}