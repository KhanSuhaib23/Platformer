package com.suhaib.game.entity.mobs; // right = 0, left = 1, 

import com.suhaib.game.graphics.Animation;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.input.GameInput;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.render.Renderer;

import static com.suhaib.game.input.UserInput.*;

public class Player extends Mob {

	private GameInput gameInput;
	private boolean jump_check = false;
	private int jumpSpeedY = 10;
	private int updating = 0;
	private boolean check = true;

	public Player(Vector2 position, Animation animation, Level level, GameInput gameInput) {
		super(position, animation, level);
		this.gameInput = gameInput;
	}


	public void render(Renderer renderer) {
//		Sprite currentSprite = Sprite.mario[0];
//		if (moving) {
//			if (direction == 0) {
//				if (animation % 12 < 4) {
//					currentSprite = Sprite.mario[2];
//				}
//				else if (animation % 12 < 8) {
//					currentSprite = Sprite.mario[4];
//				}
//				else {
//					currentSprite = Sprite.mario[6];
//				}
//			}
//			if (direction == 1) {
//				if (animation % 12 < 4) {
//					currentSprite = Sprite.mario[3];
//				}
//				else if (animation % 12 < 8) {
//					currentSprite = Sprite.mario[5];
//				}
//				else {
//					currentSprite = Sprite.mario[7];
//				}
//			}
//		}
//		else {
//			if (direction == 0) {
//				currentSprite = Sprite.mario[0];
//			}
//			else if (direction == 1) {
//				currentSprite = Sprite.mario[1];
//			}
//		}
//		if (sliding) {
//			if (direction == 0) {
//				currentSprite = Sprite.mario[9];
//			}
//			else {
//				currentSprite = Sprite.mario[8];
//			}
//		}
//		if (jumping) {
//			if (direction == 0) {
//				currentSprite = Sprite.mario[10];
//			}
//			else {
//				currentSprite = Sprite.mario[11];
//			}
//		}
		renderer.renderSprite(position, animation.getFrame());
	}

	public void update() {
		boolean left = gameInput.isDown(LEFT), right = gameInput.isDown(RIGHT), run = gameInput.isDown(RUN), jump = gameInput.isDown(JUMP);
		updating++;
		if (updating >= 10000) updating = 0;
		if (!left && right) {
			if (direction == 1) {
				sliding = true;
				if (updating % 3 == 0) velocity.x += 64;
				if (velocity.x == 0) {
					check = true;
				}
				else {
					check = false;
				}
			}
			if (check) {
				sliding = false;
				velocity.x = 32;
				direction = 0;
				check = false;
			}
			if (gameInput.isDown(RUN)) {
				if (updating % 13 == 0) velocity.x += 16;
				if (velocity.x > 4) velocity.x = 86;
			}
			else {
				velocity.x = 32;
			}
		}

		if (!right && left) {
			if (direction == 0) {
				sliding = true;
				if (updating % 3 == 0) velocity.x -= 64;
				if (velocity.x == 0) {
					check = true;
				}
				else {
					check = false;
				}
			}
			if (check) {
				sliding = false;
				velocity.x = -32;
				direction = 1;
				check = false;
			}
			if (run) {
				if (updating % 13 == 0) velocity.x -= 32;
				if (velocity.x < -4) velocity.x = -86;
			}
			else {
				velocity.x = -32;
			}
		}

		if (right && left) {
			if (velocity.x > 0) {
				if (updating % 13 == 0) velocity.x -= 32;
				if (velocity.x < 0) velocity.x = 0;
			}
			if (velocity.x < 0) {
				if (updating % 13 == 0) velocity.x += 32;
				if (velocity.x > 0) velocity.x = 0;
			}
			check = true;
		}

		if (!right && !left) {
			if (velocity.x > 0) {
				if (updating % 13 == 0) velocity.x -= 32;
				if (velocity.x < 0) velocity.x = 0;
			}
			if (velocity.x < 0) {
				if (updating % 13 == 0) velocity.x += 32;
				if (velocity.x > 0) velocity.x = 0;
			}
			check = true;
		}
		// velocity.y = 0;
		if (jump) {
			if (!jump_check) {
				if (velocity.y == 0) {
					jump_check = true;
					velocity.y = 6 * 32;
				}
			}

		}

		if (jump_check) {
			if (updating % 4 == 0) {
				velocity.y -= 16;
			}
			if (!jump) {
				if (velocity.y > 0) {
					velocity.y = 0;
				}
				else {
					if (updating % 8 == 0) velocity.y -= 32;
				}
			}

			if (collision(Vector2.add(position, velocity.up()))) {
				if (velocity.y < 0) jump_check = false;
				else jump_check = true;
				velocity.y = 0;
			}
		}
		else {
			velocity.y -= 32;
			if (collision(Vector2.add(position, velocity.up()))) {
				if (velocity.y > 0) jump_check = false;
				else jump_check = true;
				velocity.y = 0;
			}
		}

		if (velocity.x == 0 && velocity.y == 0) {
			moving = false;
			jumping = false;
		}
		else if (velocity.y != 0) {
			jumping = true;
			moving = false;
		}
		else if (velocity.x != 0 && velocity.y == 0) {
			moving = true;
			jumping = false;
		}

		animation.update((int) Math.abs(velocity.x), velocity.x >= 0 ? Animation.Direction.RIGHT : Animation.Direction.LEFT);

		move(velocity.x, velocity.y);
	}

	public RenderPosition renderPosition() {
		return position.renderPosition();
	}

}
