package com.suhaib.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.level.tile.Tile;

public class Level {
	private String path;
	private int width;
	private int height;
	private int[] tiles;
	private Display display;

	public Level(String path) {
		this.path = path;
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, this.width, this.height, tiles, 0, this.width);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(int xScroll, int yScroll, Display display) {
		this.display = display;
		display.setOffSet(xScroll, yScroll);
		int x0 = xScroll / 16;
		int x1 = (xScroll + display.WIDTH) / 16;
		int y0 = yScroll / 16;
		int y1 = (yScroll + display.HEIGHT) / 16;
		for (int y = y0; y <= y1; y++) {
			for (int x = x0; x <= x1; x++) {
				getTile(x, y).render(x, y, display);
			}
		}

	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= this.width || y < 0 || y >= this.height) return Tile.blank;
		if (tiles[x + y * this.width] == Tile.COL_GROUND) return Tile.ground;
		else if (tiles[x + y * this.width] == Tile.COL_BLOCK) return Tile.block;
		else if (tiles[x + y * this.width] == Tile.COL_SOLID_BLOCK) return Tile.solid_block;
		else if (tiles[x + y * this.width] == Tile.COL_COIN_BLOCK) return Tile.coin_block;
		else if (tiles[x + y * this.width] == Tile.COL_PIPE_TOP_1) return Tile.pipe_top_1;
		else if (tiles[x + y * this.width] == Tile.COL_PIPE_TOP_2) return Tile.pipe_top_2;
		else if (tiles[x + y * this.width] == Tile.COL_PIPE_BOTTOM_1) return Tile.pipe_bottom_1;
		else if (tiles[x + y * this.width] == Tile.COL_PIPE_BOTTOM_2) return Tile.pipe_bottom_2;
		else if (tiles[x + y * this.width] == Tile.COL_SKY) return Tile.sky;
		else return Tile.blank;
	}

}
