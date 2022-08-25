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

    public static BoxCollider relativeTo(BoxCollider collider, Vector2 pos) {
        return new BoxCollider(Vector2.add(collider.p1, pos), Vector2.add(collider.p2, pos));
    }

    private static boolean within(long v, long min, long max) {
        return v >= min && v <= max;
    }

    public static boolean doesCollide(BoxCollider b1, BoxCollider b2) {
        return (within(b1.p1.x, b2.p1.x, b2.p2.x) || within(b1.p2.x, b2.p1.x, b2.p2.x)) &&
                (within(b1.p1.y, b2.p1.y, b2.p2.y) || within(b1.p2.y, b2.p1.y, b2.p2.y));
    }
}
