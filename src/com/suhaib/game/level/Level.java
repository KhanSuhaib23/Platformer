package com.suhaib.game.level;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.level.tile.Tile;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.TilePosition;
import com.suhaib.game.resource.TileSet;

public class Level {
	private final int width;
	private final int height;
	private final int[] tiles;
	private final TileSet tileSet;
	private final TilePosition spawnLocation;

	public Level(String path, TileSet tileSet, TilePosition spawnLocation) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			int[] header = Arrays.stream(reader.readLine().split(" "))
					.map(String::trim)
					.mapToInt(Integer::parseInt)
					.toArray();

			if (header.length != 2) {
				throw new RuntimeException("Level file " + path + " corrupted. Header should contain two integers separated by a space.");
			}

			int width = header[0];
			int height = header[1];

			this.width = width;
			this.height = height;

			this.tiles = Arrays.stream(reader.readLine().split(" "))
					.map(String::trim)
					.mapToInt(Integer::parseInt)
					.toArray();

			if (this.tiles.length != this.width * this.height) {
				throw new RuntimeException("Level file " + path + " corrupted. Should contain width * height = " + width * height + "number of tile values");
			}

			this.tileSet = tileSet;
			this.spawnLocation = spawnLocation;

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Tile getTile(TilePosition tilePosition) {
		if (tilePosition.x() < 0 || tilePosition.x() >= width || tilePosition.y() < 0 || tilePosition.y() >= height) {
			return tileSet.getBlank();
		}
		return tileSet.get(tiles[(int) (tilePosition.x() + tilePosition.y() * width)]);
	}

	public TilePosition spawnLocation() {
		return spawnLocation;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}
}
