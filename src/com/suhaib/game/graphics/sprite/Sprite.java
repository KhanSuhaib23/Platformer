package com.suhaib.game.graphics.sprite;

public class Sprite {
	public int size;
	private int x, y;
	private SpriteSheet sheet;
	public int[] pixels;

	public static Sprite ground = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite sky = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite coin_block = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite block = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite solid_block = new Sprite(16, 2, 1, SpriteSheet.tiles);
	public static Sprite blank = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite pipe_top_1 = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite pipe_top_2 = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite pipe_bottom_1 = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite pipe_bottom_2 = new Sprite(16, 1, 2, SpriteSheet.tiles);

	public static Sprite[] mario = { 
			new Sprite(16, 0, 0, SpriteSheet.sprites),  // 0 stand right
			new Sprite(16, 1, 0, SpriteSheet.sprites),  // 1 stand left
			new Sprite(16, 2, 0, SpriteSheet.sprites),  // 2 run right
			new Sprite(16, 3, 0, SpriteSheet.sprites),  // 3 run left
			new Sprite(16, 4, 0, SpriteSheet.sprites),  // 4 run right
			new Sprite(16, 5, 0, SpriteSheet.sprites),  // 5 run left
			new Sprite(16, 6, 0, SpriteSheet.sprites),  // 6 run right
			new Sprite(16, 7, 0, SpriteSheet.sprites),  // 7 run left
			new Sprite(16, 0, 1, SpriteSheet.sprites),  // 8 slide right
			new Sprite(16, 1, 1, SpriteSheet.sprites),  // 9 slide left
			new Sprite(16, 2, 1, SpriteSheet.sprites),  //10 jump right
			new Sprite(16, 3, 1, SpriteSheet.sprites),  // 11 jump left
			};

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.size = size;
		this.x = x * size;
		this.y = y * size;
		pixels = new int[size * size];
		this.sheet = sheet;
		load();
	}

	private void load() {
		for (int y = 0; y < this.size; y++) {
			for (int x = 0; x < this.size; x++) {
				this.pixels[x + y * this.size] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.width];
			}
		}
	}
}
