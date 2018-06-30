package com.suhaib.game.level.tile;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;

public class NonSolidTile extends Tile{

	public NonSolidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Display display) {
		display.renderTile(x * 16, y * 16, this);
	}

	public boolean solid() {
		return false;
	}


	
	

}
