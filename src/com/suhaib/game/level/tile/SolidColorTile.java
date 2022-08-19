package com.suhaib.game.level.tile;

import com.suhaib.game.math.Slice2D;

public class SolidColorTile extends Tile {
    private final int color;
    public SolidColorTile(int width, int height, boolean solid, int color) {
        super(width, height, solid);
        this.color = color;
    }

    @Override
    public int pixel(int x, int y) {
        return color;
    }
}
