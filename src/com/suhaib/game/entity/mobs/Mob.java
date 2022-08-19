package com.suhaib.game.entity.mobs;

import com.suhaib.game.entity.Entity;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.Level;
import com.suhaib.game.math.TilePosition;

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

	protected int sx = 0;
	protected int sy = 0;

	public Mob(int x, int y, Sprite[] sprite, Level level) {
		super(x, y, sprite, level);
	}

	public void render(Display display) {

	}

	public void move(int xMove, int yMove) {
		if (xMove != 0 && yMove != 0) {
			move(xMove, 0);
			move(0, yMove);
			return;
		}
		if (!collision(x + xMove, y + yMove)) {
			x += xMove;
			y += yMove;
		}

	}

	public boolean collision(int x, int y) {
		int xt = 0, yt = 0;
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			xt = (x + c % 2 * 9 + 3) / 16;
			yt = (y + c / 2 * 9 + 6) / 16;
			if (level.getTile(new TilePosition(xt, yt)).solid()) solid = true;
		}
		return solid;
	}

}
