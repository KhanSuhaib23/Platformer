package com.suhaib.game.resource;

import com.suhaib.game.level.tile.SolidColorTile;
import com.suhaib.game.level.tile.TexturedTile;
import com.suhaib.game.level.tile.Tile;
import com.suhaib.game.math.Slice2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class TileSet {
    private int[] pixels;
    private int tileWidth, tileHeight;
    private Tile[] tiles;
    private Tile blank;

    public TileSet(String path, int tileWidth, int tileHeight, List<Tile.Definition> definitions) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int totalWidth = image.getWidth();
            int totalHeight = image.getHeight();

            pixels = new int[totalWidth * totalHeight];

            image.getRGB(0, 0, totalWidth, totalHeight, pixels, (totalHeight - 1) * totalWidth, -totalWidth);

            this.tileHeight = tileHeight;
            this.tileWidth = tileWidth;


            this.blank = new SolidColorTile(tileWidth, tileHeight, true, 0);

            tiles = new Tile[definitions.size()];

            for (int i = 0; i < definitions.size(); ++i) {
                Tile.Definition definition = definitions.get(i);
                int xPos = definition.x() * tileWidth;
                int yPos = definition.y() * tileHeight;
                Slice2D slice = new Slice2D(xPos, yPos, totalWidth, pixels);
                tiles[i] = new TexturedTile(tileWidth, tileHeight, definition.solid(), slice);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Tile getBlank() {
        return blank;
    }

    public Tile get(int i) {
        if (i < tiles.length) {
            return tiles[i];
        } else {
            return blank;
        }
    }
}
