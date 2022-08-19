package com.suhaib.game.level.tile;

import com.suhaib.game.math.Slice2D;

public abstract class Tile {

	private final boolean solid;
	private final int width, height;

	public Tile(int width, int height, boolean solid){
		this.width = width;
		this.height = height;
		this.solid = solid;
	}

	public abstract int pixel(int x, int y);

	public boolean solid() {
		return solid;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}
}
