package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public record Vector2(long x, long y) {

    public RenderPosition renderPosition() {
        return new RenderPosition(x / Constants.RENDER_SCALE, y / Constants.RENDER_SCALE);
    }
}
