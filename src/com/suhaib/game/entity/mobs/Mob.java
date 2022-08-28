package com.suhaib.game.entity.mobs;

import com.suhaib.game.entity.Entity;
import com.suhaib.game.graphics.Animation;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.physics.BoxCollider;
import com.suhaib.game.render.Renderer;

import java.util.Optional;

public class Mob extends Entity {

	protected boolean moving = false;
	protected boolean jumping = false;
	protected boolean sliding = false;
	protected int direction = 0;

	protected double time0 = 0;
	protected double time = 0;

	protected double jump_time0 = 0;
	protected double jump_time = 0;

	protected boolean check = true;
	protected boolean jump_check = true;

	protected Vector2 velocity = new Vector2(0, 0);
	public BoxCollider collider;

	public Mob(Vector2 position, Animation animation, BoxCollider collider, Level level) {
		super(position, animation, level);
		this.collider = collider;
	}

	public void render(Renderer renderer) {

	}

	public Vector2 move() {
		position.add(velocity);

		return collision(position);
	}

	// TODO(suhaibnk): some calculations here are on render position, which is incorrect
	// Change this when collision system is implemented
	public Vector2 collision(Vector2 position) {
		Vector2 adjust = new Vector2(0, 0);
		BoxCollider mobCollider = BoxCollider.relativeTo(this.collider, position);

		for (BoxCollider collider : level.colliders()) {
			Optional<Vector2> v = BoxCollider.howMuchCollision(mobCollider, collider);
			v.ifPresent(x -> adjust.add(x));
		}

		return adjust;
	}

}
