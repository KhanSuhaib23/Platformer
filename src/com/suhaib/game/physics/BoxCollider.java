package com.suhaib.game.physics;

import com.suhaib.game.math.Vector2;

import java.util.Optional;

public record BoxCollider(BoundingBox b) {
    public BoxCollider(Vector2 p1, Vector2 p2) {
        this(new BoundingBox(p1, p2));
    }

    public Vector2[] corners() {
        return new Vector2[] {
            p1(),
            new Vector2(p2().x, p1().y),
            p2(),
            new Vector2(p1().x, p2().y)
        };
    }

    public Vector2 p1() {
        return b.p1();
    }

    public Vector2 p2() {
        return b.p2();
    }

    public static BoxCollider relativeTo(BoxCollider collider, Vector2 pos) {
        return new BoxCollider(Vector2.add(collider.p1(), pos), Vector2.add(collider.p2(), pos));
    }

    private static boolean within(long v, long min, long max) {
        return v >= min && v <= max;
    }

    public BoxCollider expanded(BoxCollider b1) {
        // assume b1 is the dynamic object, reduce it to a point. Increase b2 by b1
        Vector2 origin = Vector2.add(b1.p1(), b1.p2()).scaleDown(2); // get the middle of bounding box b1

        long halfX = Math.abs(Vector2.sub(origin, b1.p1()).x);
        long halfY = Math.abs(Vector2.sub(origin, b1.p1()).y);

        Vector2 half = new Vector2(halfX, halfY);

        BoundingBox b2Expanded = new BoundingBox(Vector2.sub(p1(), half), Vector2.add(p2(), half));

        return new BoxCollider(b2Expanded);
    }

    public static Optional<Vector2> howMuchCollision(BoxCollider b1, BoxCollider b2) {
        // assume b1 is the dynamic object, reduce it to a point. Increase b2 by b1
        Vector2 origin = Vector2.add(b1.p1(), b1.p2()).scaleDown(2); // get the middle of bounding box b1

        long halfX = Math.abs(Vector2.sub(origin, b1.p1()).x);
        long halfY = Math.abs(Vector2.sub(origin, b1.p1()).y);

        Vector2 half = new Vector2(halfX, halfY);

        BoundingBox b2Expanded = new BoundingBox(Vector2.sub(b2.p1(), half), Vector2.add(b2.p2(), half));

        long x = origin.x;
        long y = origin.y;
        long x0 = b2Expanded.p1().x;
        long x1 = b2Expanded.p2().x;
        long y0 = b2Expanded.p1().y;
        long y1 = b2Expanded.p2().y;

        if (within(x, x0, x1) && within(y, y0, y1)) {
            long xMin = Math.abs(x - x0) <= Math.abs(x - x1) ? x - x0 : x - x1;
            long yMin = Math.abs(y - y0) <= Math.abs(y - y1) ? y - y0 : y - y1;

            if (Math.abs(yMin) <= Math.abs(xMin)) {
                return Optional.of(new Vector2(0, yMin));
            } else {
                return Optional.of(new Vector2(xMin, 0));
            }
        } else {
            return Optional.empty();
        }
    }
}
