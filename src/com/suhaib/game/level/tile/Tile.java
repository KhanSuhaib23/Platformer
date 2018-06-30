package com.suhaib.game.level.tile;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;

public abstract class Tile {
	
	public Sprite sprite;
	
	public static Tile ground = new SolidTile(Sprite.ground);
	public static final int COL_GROUND = 0xff7f3300;
	
	public static Tile block = new SolidTile(Sprite.block);
	public static final int COL_BLOCK = 0xff7c3a0e;
	
	public static Tile solid_block = new SolidTile(Sprite.solid_block);
	public static final int COL_SOLID_BLOCK = 0xff7c3a0f;
	
	public static Tile coin_block = new SolidTile(Sprite.coin_block);
	public static final int COL_COIN_BLOCK = 0xffffe41c;
	
	public static Tile sky = new NonSolidTile(Sprite.sky);
	public static final int COL_SKY = 0xff0094ff;
	
	public static Tile pipe_top_1 = new SolidTile(Sprite.pipe_top_1);
	public static final int COL_PIPE_TOP_1 = 0xff267f10;
	
	public static Tile pipe_top_2 = new SolidTile(Sprite.pipe_top_2);
	public static final int COL_PIPE_TOP_2 = 0xff267f11;
	
	public static Tile pipe_bottom_1 = new SolidTile(Sprite.pipe_bottom_1);
	public static final int COL_PIPE_BOTTOM_1 = 0xff26FF00;
	
	public static Tile pipe_bottom_2 = new SolidTile(Sprite.pipe_bottom_2);
	public static final int COL_PIPE_BOTTOM_2 = 0xff26FF01;
	
	public static Tile blank = new NonSolidTile(Sprite.blank);
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public abstract void render(int x, int y, Display display);
	public abstract boolean solid();
}
