package com.suhaib.game.graphics.sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public int width;
	public int height;
	public int[] pixels;
	private String path;

	public static SpriteSheet tiles = new SpriteSheet("/res/raw/tiles.png");
	public static SpriteSheet sprites = new SpriteSheet("/res/raw/sprite.png");

	public SpriteSheet(String path) {
		this.path = path;
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(this.path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
