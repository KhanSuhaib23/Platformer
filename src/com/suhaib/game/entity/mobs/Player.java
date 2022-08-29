package com.suhaib.game.entity.mobs; // right = 0, left = 1, 

import com.suhaib.game.graphics.Animation;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.input.GameInput;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.physics.BoxCollider;
import com.suhaib.game.render.Constants;
import com.suhaib.game.render.Renderer;

import static com.suhaib.game.input.UserInput.*;

public class Player extends Mob {

	private GameInput gameInput;

	private static final long xOffsetCollider = 3;

	public Player(Vector2 position, Animation animation, Level level, GameInput gameInput) {
		super(position, animation,
				new BoxCollider(new Vector2(xOffsetCollider * Constants.RENDER_SCALE, 0),
								new Vector2((16 - xOffsetCollider) * Constants.RENDER_SCALE, 16 * Constants.RENDER_SCALE)), level);
		this.gameInput = gameInput;
	}


	public void render(Renderer renderer) {
		renderer.renderSprite(position, animation.getFrame());
		renderer.renderCollisionBox(position, collider, 0xffff0000);
	}

	public void update() {
		final int AIR_RESISTANCE = 6;

		boolean left = gameInput.isDown(LEFT), right = gameInput.isDown(RIGHT), run = gameInput.isDown(RUN), jump = gameInput.isDown(JUMP);

		if (left) {
			acceleration.x = -8 * (run ? 2 : 1);
		} else if (right) {
			acceleration.x = 8 * (run ? 2 : 1);
		} else {
			acceleration.x = 0;
		}

		if (jump) {
			acceleration.y = 32;
		} else {
			acceleration.y = GRAVITY;
		}

		if (velocity.x / AIR_RESISTANCE == 0) {
			velocity.x = 0;
		}
		if (velocity.y / AIR_RESISTANCE == 0) {
			velocity.y = 0;
		}

		velocity.sub(Vector2.scaleDown(velocity, AIR_RESISTANCE));
		velocity.add(acceleration);

		Vector2 adjust = move();

		if (adjust.y < 0 && velocity.y < 0 && !jump) {
			velocity.y = 0;
			jump_check = false;
		}


		position.sub(adjust);

		animation.update((int) Math.abs(velocity.x), velocity.x >= 0 ? Animation.Direction.RIGHT : Animation.Direction.LEFT);
	}

}
