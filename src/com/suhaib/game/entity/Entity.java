package com.suhaib.game.entity;

import com.suhaib.game.graphics.Animation;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.render.Renderer;

public class Entity {
	protected Vector2 position;
	protected Animation animation;
	protected Level level;


	public Entity(Vector2 position, Animation animation, Level level) {
		this.position = position;
		this.animation = animation;
		this.level = level;
	}

	public void render(Renderer renderer) {

	}

	public void update() {

	}

	public Vector2 position() {
		return position;
	}


}
