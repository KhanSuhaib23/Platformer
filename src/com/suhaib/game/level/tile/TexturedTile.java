package com.suhaib.game.level.tile;

import com.suhaib.game.math.Slice2D;

public class TexturedTile extends Tile {
    private final Slice2D pixels;

    public TexturedTile(int width, int height, boolean solid, Slice2D pixels) {
        super(width, height, solid);
        this.pixels = pixels;
    }

    @Override
    public int pixel(int x, int y) {
        return pixels.get(x, y);
    }
}
