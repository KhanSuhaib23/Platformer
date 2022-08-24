package com.suhaib.game.graphics.sprite;

import com.suhaib.game.math.Slice2D;

public class Sprite {
	public record Definition(int x, int y) {

	}

	public int width, height;
	public Slice2D pixels;

	public static Sprite[] mario = { 
//			new Sprite(16, 0, 0, SpriteSheet.sprites),  // 0 stand right
//			new Sprite(16, 1, 0, SpriteSheet.sprites),  // 1 stand left
//			new Sprite(16, 2, 0, SpriteSheet.sprites),  // 2 run right
//			new Sprite(16, 3, 0, SpriteSheet.sprites),  // 3 run left
//			new Sprite(16, 4, 0, SpriteSheet.sprites),  // 4 run right
//			new Sprite(16, 5, 0, SpriteSheet.sprites),  // 5 run left
//			new Sprite(16, 6, 0, SpriteSheet.sprites),  // 6 run right
//			new Sprite(16, 7, 0, SpriteSheet.sprites),  // 7 run left
//			new Sprite(16, 0, 1, SpriteSheet.sprites),  // 8 slide right
//			new Sprite(16, 1, 1, SpriteSheet.sprites),  // 9 slide left
//			new Sprite(16, 2, 1, SpriteSheet.sprites),  //10 jump right
//			new Sprite(16, 3, 1, SpriteSheet.sprites),  // 11 jump left
			};

	public Sprite(int width, int height, Slice2D pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
}
