package com.suhaib.game.graphics;

import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.tile.Tile;

public class Display {

	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	public int xOffSet = 0, yOffSet = 0;

	public Display(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xffffffff;
		}
	}
}
