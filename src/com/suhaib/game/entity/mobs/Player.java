package com.suhaib.game.entity.mobs; // right = 0, left = 1, 

import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.input.Keyboard;
import com.suhaib.game.level.Level;

public class Player extends Mob {

	private Keyboard key;
	private int animation = 0;
	private boolean jump_check = false;
	private int jumpSpeedY = 10;
	private int updating = 0;
	private boolean check = true;

	public Player(int x, int y, Sprite[] sprite, Level level) {
		super(x, y, sprite, level);
	}

	public void initializeKey(Keyboard key) {
		this.key = key;
	}

	public void render(Display display) {
		Sprite currentSprite = Sprite.mario[0];
		if (moving) {
			if (direction == 0) {
				if (animation % 12 < 4) {
					currentSprite = Sprite.mario[2];
				}
				else if (animation % 12 < 8) {
					currentSprite = Sprite.mario[4];
				}
				else {
					currentSprite = Sprite.mario[6];
				}
			}
			if (direction == 1) {
				if (animation % 12 < 4) {
					currentSprite = Sprite.mario[3];
				}
				else if (animation % 12 < 8) {
					currentSprite = Sprite.mario[5];
				}
				else {
					currentSprite = Sprite.mario[7];
				}
			}
		}
		else {
			if (direction == 0) {
				currentSprite = Sprite.mario[0];
			}
			else if (direction == 1) {
				currentSprite = Sprite.mario[1];
			}
		}
		if (sliding) {
			if (direction == 0) {
				currentSprite = Sprite.mario[9];
			}
			else {
				currentSprite = Sprite.mario[8];
			}
		}
		if (jumping) {
			if (direction == 0) {
				currentSprite = Sprite.mario[10];
			}
			else {
				currentSprite = Sprite.mario[11];
			}
		}
		display.renderSprite(x, y, currentSprite);
	}

	public void update() {
		animation++;
		updating++;
		if (updating >= 10000) updating = 0;
		if (!key.left && key.right) {
			if (direction == 1) {
				sliding = true;
				if (updating % 3 == 0) sx+=2;
				if (sx == 0) {
					check = true;
				}
				else {
					check = false;
				}
			}
			if (check) {
				sliding = false;
				sx = 1;
				direction = 0;
				check = false;
			}
			if (key.run) {
				if (updating % 13 == 0) sx++;
				if (sx > 4) sx = 4;
			}
			else {
				sx = 1;
			}
		}

		if (!key.right && key.left) {
			if (direction == 0) {
				sliding = true;
				if (updating % 3 == 0) sx-=2;
				if (sx == 0) {
					check = true;
				}
				else {
					check = false;
				}
			}
			if (check) {
				sliding = false;
				sx = -1;
				direction = 1;
				check = false;
			}
			if (key.run) {
				if (updating % 13 == 0) sx--;
				if (sx < -4) sx = -4;
			}
			else {
				sx = -1;
			}
		}

		if (key.right && key.left) {
			if (sx > 0) {
				if (updating % 13 == 0) sx--;
				if (sx < 0) sx = 0;
			}
			if (sx < 0) {
				if (updating % 13 == 0) sx++;
				if (sx > 0) sx = 0;
			}
			check = true;
		}

		if (!key.right && !key.left) {
			if (sx > 0) {
				if (updating % 13 == 0) sx--;
				if (sx < 0) sx = 0;
			}
			if (sx < 0) {
				if (updating % 13 == 0) sx++;
				if (sx > 0) sx = 0;
			}
			check = true;
		}
		// sy = 0;
		if (key.jump) {
			if (!jump_check) {
				if (sy == 0) {
					jump_check = true;
					sy = -6;
				}
			}

		}

		if (jump_check) {
			if (updating % 4 == 0) {
				sy++;
			}
			if (!key.jump) {
				if (sy < 0) {
					sy = 0;
				}
				else {
					if (updating % 8 == 0) sy++;
				}
			}

			if (collision(x, y + sy)) {
				if (sy > 0) jump_check = false;
				else jump_check = true;
				sy = 0;
			}
		}
		else {
			sy++;
			if (collision(x, y + sy)) {
				if (sy > 0) jump_check = false;
				else jump_check = true;
				sy = 0;
			}
		}

		if (sx == 0 && sy == 0) {
			moving = false;
			jumping = false;
		}
		else if (sy != 0) {
			jumping = true;
			moving = false;
		}
		else if (sx != 0 && sy == 0) {
			moving = true;
			jumping = false;
		}

		move(sx, sy);
	}

}
