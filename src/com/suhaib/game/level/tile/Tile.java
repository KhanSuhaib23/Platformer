package com.suhaib.game.level.tile;


public abstract class Tile {
	public record Definition(int x, int y, boolean solid) {

	}

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
