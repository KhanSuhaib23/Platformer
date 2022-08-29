package com.suhaib.game.math;

import com.suhaib.game.render.Constants;

public class Vector2 {
    public long x;
    public long y;

    public Vector2(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public RenderPosition renderPosition() {
        return new RenderPosition(Math.floorDiv(x, Constants.RENDER_SCALE), Math.floorDiv(y, Constants.RENDER_SCALE));
    }

    public TilePosition tilePosition() {
        return new TilePosition(Math.floorDiv(x, Constants.RENDER_SCALE * Constants.TILE_SIZE), Math.floorDiv(y, Constants.RENDER_SCALE * Constants.TILE_SIZE));
    }

    public static Vector2 add(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2 scaleDown(Vector2 v, long s) {
        return new Vector2(v.x / s, v.y / s);
    }

    public Vector2 scaleDown(long s) {
        this.x /= s;
        this.y /= s;

        return this;
    }

    public static Vector2 sub(Vector2 v1, Vector2 v2) {
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector2 bound(Vector2 v) {
        return new Vector2(Math.max(0 , v.x), Math.max(0, v.y));
    }

    public Vector2 add(Vector2 other) {
        x += other.x;
        y += other.y;

        return this;
    }

    public Vector2 sub(Vector2 other) {
        x -= other.x;
        y -= other.y;

        return this;
    }

    public Vector2 bound() {
        x = Math.max(0, x);
        y = Math.max(0, y);

        return this;
    }

    public Vector2 up() {
        return new Vector2(0, y);
    }

    public Vector2 right() {
        return new Vector2(x, 0);
    }

    @Override
    public String toString() {
        return "Vector2[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }
}
