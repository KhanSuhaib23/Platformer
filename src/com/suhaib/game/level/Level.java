package com.suhaib.game.level;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.suhaib.game.graphics.Display;
import com.suhaib.game.level.tile.Tile;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.TilePosition;
import com.suhaib.game.math.Vector2;
import com.suhaib.game.physics.BoxCollider;
import com.suhaib.game.resource.TileSet;

public class Level {
	private record Pair(long y1, long y2) {

	}

	private final int width;
	private final int height;
	private final int[] tiles;
	private final TileSet tileSet;
	private final TilePosition spawnLocation;
	private final List<BoxCollider> colliders;

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

			this.colliders = new ArrayList<>();
			Map<Pair, Long> pairStart = new HashMap<>();

			for (int x = 0; x < width; ++x) {
				Set<Pair> newPairs = new HashSet<>();
				for (int y = 0; y < height; ) {
					if (tileSet.get(tiles[x + y * width]).solid()) {
						long y0 = y;
						while (y < height && tileSet.get(tiles[x + y * width]).solid()) {
							y++;
						}
						long y1 = y;

						newPairs.add(new Pair(y0, y1));

					} else {
						y++;
					}
				}

				final long x1 = x;

				pairStart.entrySet()
						.stream()
						.filter(p -> !newPairs.contains(p.getKey()))
						.forEach(p -> {
							colliders.add(new BoxCollider(new TilePosition(p.getValue(), p.getKey().y1()).worldPosition(),
															new TilePosition(x1, p.getKey().y2()).worldPosition()));
						});

				pairStart = pairStart.entrySet()
						.stream()
						.filter(p -> newPairs.contains(p.getKey()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

				for (Pair p : newPairs) {
					Long st = pairStart.get(p);
					if (st == null) {
						pairStart.put(p, (long) x);
					}
				}
			}

			pairStart.entrySet()
					.stream()
					.forEach(p -> {
						colliders.add(new BoxCollider(new TilePosition(p.getValue(), p.getKey().y1()).worldPosition(),
								new TilePosition(width - 1, p.getKey().y2()).worldPosition()));
					});

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

	public List<BoxCollider> colliders() {
		return colliders;
	}
}
