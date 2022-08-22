package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public record RenderPosition(long x, long y) {
    public TilePosition tilePosition() {
        return new TilePosition(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE);
    }
}
