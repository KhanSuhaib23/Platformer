package com.suhaib.game.math;

public record Slice2D(int x, int y, int s, int[] base) {
    public int get(int x, int y) {
        return base[(this.x + x) + s * (this.y + y)];
    }
}
