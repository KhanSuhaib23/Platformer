package com.suhaib.game.graphics.sprite;

import com.suhaib.game.math.Slice2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public int width;
	public int height;
	public int[] pixels;
	private Map<String, Sprite> sprites;

	public SpriteSheet(String path, int spriteWidth, int spriteHeight, Map<String, Sprite.Definition> spriteDefinitions) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.sprites = new HashMap<>();
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, pixels, (this.height - 1) * this.width, -this.width);

			sprites = spriteDefinitions.entrySet().stream()
					.map(e -> Map.entry(e.getKey(), new Sprite(spriteWidth, spriteHeight,
							new Slice2D(e.getValue().x() * spriteWidth, e.getValue().y() * spriteHeight, width, pixels))))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sprite get(String name) {
		return sprites.get(name);
	}

}
