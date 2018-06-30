package com.suhaib.game.graphics;

import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.tile.Tile;

public class Display {

	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	private int xOffSet = 0, yOffSet = 0;

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

	public void renderTile(int xPosition, int yPosition, Tile tile) {

		xPosition -= xOffSet;
		yPosition -= yOffSet;
		int xAbsolute;
		int yAbsolute;
		for (int y = 0; y < tile.sprite.size; y++) {
			yAbsolute = y + yPosition;
			if (yAbsolute < -16 || yAbsolute >= HEIGHT) break;
			if (yAbsolute < 0) yAbsolute = 0;
			for (int x = 0; x < tile.sprite.size; x++) {
				xAbsolute = x + xPosition;
				if (xAbsolute < -16 || xAbsolute >= WIDTH) break;
				if (xAbsolute < 0) xAbsolute = 0;
				pixels[xAbsolute + yAbsolute * WIDTH] = tile.sprite.pixels[x + y * tile.sprite.size];
			}
		}
	}

	public void renderSprite(int xPosition, int yPosition, Sprite sprite) {
		xPosition -= xOffSet;
		yPosition -= yOffSet;
		int xAbsolute;
		int yAbsolute;
		for (int y = 0; y < sprite.size; y++) {
			yAbsolute = y + yPosition;
			if (yAbsolute < -16 || yAbsolute >= HEIGHT) break;
			if (yAbsolute < 0) yAbsolute = 0;
			for (int x = 0; x < sprite.size; x++) {
				xAbsolute = x + xPosition;
				if (xAbsolute < -16 || xAbsolute >= WIDTH) break;
				if (xAbsolute < 0) xAbsolute = 0;
				int color = sprite.pixels[x + y * sprite.size];
				if (color != 0xffff00ff) pixels[xAbsolute + yAbsolute * WIDTH] = color;
			}
		}
	}

	public void setOffSet(int xOffSet, int yOffSet) {
		this.xOffSet = xOffSet;
		this.yOffSet = yOffSet;
	}
}
