package com.suhaib.game.entity;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.render.Renderer;

public class Entity {
	protected Vector2 position;
	protected Sprite[] sprite;
	protected Level level;


	public Entity(Vector2 position, Sprite[] sprite, Level level) {
		this.position = position;
		this.sprite = new Sprite[sprite.length];
		for (int i = 0; i < sprite.length; i++) {
			this.sprite[i] = sprite[i];
			this.level = level;
		}

	}

	public void render(Renderer renderer) {

	}

	public void update() {

	}

	public Vector2 position() {
		return position;
	}


}
