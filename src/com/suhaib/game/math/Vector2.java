package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public record Vector2(long x, long y) {

    Vector2 renderPosition() {
        return new Vector2(x / Constants.RENDER_SCALE, y / Constants.RENDER_SCALE);
    }
}
