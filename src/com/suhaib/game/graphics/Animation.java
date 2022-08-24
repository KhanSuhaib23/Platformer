package com.suhaib.game.graphics;

import com.suhaib.game.graphics.sprite.Sprite;

import java.util.List;

public class Animation {
    public enum Direction {
        LEFT(0),
        RIGHT(1);

        private int index;

        Direction(int index) {
            this.index = index;
        }

        public int index() {
            return index;
        }
    }
    private final int MAX_TS_FRAMES = 120;

    private int maxFrames;
    private int currentFrame;
    private List<Sprite> frames[];
    private int tillFrameChange;
    private Direction dir;

    public Animation(List<Sprite> left, List<Sprite> right) {
        maxFrames = left.size();
        this.frames = new List[2];
        frames[0] = left;
        frames[1] = right;
        currentFrame = 0;
        tillFrameChange = MAX_TS_FRAMES;
        dir = Direction.RIGHT;
    }

    public Sprite getFrame() {
        return frames[dir.index()].get(currentFrame);
    }

    public void update(int ts, Direction dir) {
        this.dir = dir;
        tillFrameChange -= ts;

        if (tillFrameChange <= 0) {
            tillFrameChange += MAX_TS_FRAMES;
            currentFrame = (currentFrame + 1) % maxFrames;
        }
    }
}
