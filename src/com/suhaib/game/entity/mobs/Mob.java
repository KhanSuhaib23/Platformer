package com.suhaib.game.entity.mobs;

import com.suhaib.game.entity.Entity;
import com.suhaib.game.graphics.Animation;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.TilePosition;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.physics.BoxCollider;
import com.suhaib.game.render.Constants;
import com.suhaib.game.render.Renderer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	protected BoxCollider collider;

	public Mob(Vector2 position, Animation animation, BoxCollider collider, Level level) {
		super(position, animation, level);
		this.collider = collider;
	}

	public void render(Renderer renderer) {

	}

	// TODO(suhaibnk): why cant this me velocity
	// TODO(suhaibnk): why does it need to separate x and y component
	public void move(long xMove, long yMove) {
		if (xMove != 0 && yMove != 0) {
			move(xMove, 0);
			move(0, yMove);
			return;
		}
		Vector2 move = new Vector2(xMove, yMove);
		if (!collision(Vector2.add(position, move))) {
			position.add(move);
		}

	}

	// TODO(suhaibnk): some calculations here are on render position, which is incorrect
	// Change this when collision system is implemented
	public boolean collision(Vector2 position) {
		BoxCollider mobCollider = BoxCollider.relativeTo(this.collider, position);

		for (BoxCollider collider : level.colliders()) {
			if (BoxCollider.doesCollide(mobCollider, collider)) {
				return true;
			}
		}

		return false;
	}

}
