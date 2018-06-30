package com.suhaib.game.entity;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.Level;

public class Entity {
	public int x, y;
	protected Sprite[] sprite;
	protected Level level;

	public Entity(int x, int y, Sprite[] sprite, Level level) {
		this.x = x;
		this.y = y;
		this.sprite = new Sprite[sprite.length];
		for (int i = 0; i < sprite.length; i++) {
			this.sprite[i] = sprite[i];
			this.level = level;
		}

	}

	public void render(Display display) {

	}

	public void update() {

	}


}
