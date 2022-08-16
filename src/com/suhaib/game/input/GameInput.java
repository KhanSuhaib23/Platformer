package com.suhaib.game.input;

public interface GameInput {
    KeyState getKeyState(UserInput input);
    boolean isUp(UserInput input);
    boolean isDown(UserInput input);
}
