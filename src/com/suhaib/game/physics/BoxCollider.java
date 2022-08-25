package com.suhaib.game.physics;

import com.suhaib.game.math.Vector2;

public record BoxCollider(Vector2 p1, Vector2 p2) {

    public Vector2[] corners() {
        return new Vector2[] {
            p1,
            new Vector2(p2.x, p1.y),
            p2,
            new Vector2(p1.x, p2.y)
        };
    }
}
