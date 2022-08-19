package com.suhaib.game.math;

public record Slice2D(int x, int y, int w, int h, int[] base) {
    public int get(int x, int y) {
        return base[(this.x + x) + w * (this.y + y)];
    }
}
