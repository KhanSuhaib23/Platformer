package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public record TilePosition(long x, long y) {

    public RenderPosition renderPosition() {
        return new RenderPosition(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
    }

    public Vector2 worldPosition() {
        return new Vector2(x * Constants.TILE_SIZE * Constants.RENDER_SCALE, y * Constants.TILE_SIZE * Constants.RENDER_SCALE);
    }
}
