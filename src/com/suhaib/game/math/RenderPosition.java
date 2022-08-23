package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public record RenderPosition(long x, long y) {
    public TilePosition tilePosition() {
        if (x < 0) {
            return new TilePosition((x - Constants.TILE_SIZE) / Constants.TILE_SIZE, (y - Constants.TILE_SIZE) / Constants.TILE_SIZE);
        } else {
            return new TilePosition(x / Constants.TILE_SIZE, y / Constants.TILE_SIZE);
        }
    }

    public Vector2 worldPosition() {
        return new Vector2(x * Constants.RENDER_SCALE, y * Constants.RENDER_SCALE);
    }
}
