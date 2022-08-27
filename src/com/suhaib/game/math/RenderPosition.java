package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public record RenderPosition(long x, long y) {
    public TilePosition tilePosition() {
        return new TilePosition(Math.floorDiv(x, Constants.TILE_SIZE), Math.floorDiv(y, Constants.TILE_SIZE));
    }

    public Vector2 worldPosition() {
        return new Vector2(x * Constants.RENDER_SCALE, y * Constants.RENDER_SCALE);
    }
}
