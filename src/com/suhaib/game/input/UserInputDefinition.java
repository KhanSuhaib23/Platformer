package com.suhaib.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserInputDefinition implements KeyListener, GameInput {
    private enum KeyEventType {
        UP, DOWN
    }

    private final Map<Integer, UserInput> inputMap;
    private final Map<UserInput, KeyState> inputState;

    private UserInputDefinition(Builder builder) {
        this.inputMap = builder.inputMap;
        this.inputState = this.inputMap.values().stream()
                .collect(Collectors.toMap(Function.identity(), i -> KeyState.NOT_HELD));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        updateKey(e, KeyEventType.DOWN);
    }

    private void updateKey(KeyEvent e, KeyEventType type) {
        Optional.ofNullable(inputMap.get(e.getKeyCode()))
                .ifPresent(input -> inputState.put(input, newKeyState(inputState.get(input), type)));
    }

    @Override
    public KeyState getKeyState(UserInput input) {
        return inputState.get(input);
    }

    @Override
    public boolean isUp(UserInput input) {
        KeyState s = inputState.get(input);
        return s == KeyState.RELEASED || s == KeyState.NOT_HELD;
    }

    @Override
    public boolean isDown(UserInput input) {
        KeyState s = inputState.get(input);
        return s == KeyState.TAPPED || s == KeyState.HELD;
    }

    private KeyState newKeyState(KeyState oldKeyState, KeyEventType eventType) {
        if (eventType == KeyEventType.UP) {
            return switch (oldKeyState) {
                case NOT_HELD, RELEASED -> KeyState.NOT_HELD;
                case HELD, TAPPED -> KeyState.RELEASED;
            };
        } else {
            return switch (oldKeyState) {
                case NOT_HELD, RELEASED -> KeyState.TAPPED;
                case TAPPED, HELD -> KeyState.HELD;
            };
        }
    }

    public static Builder define() {
        return new Builder();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        updateKey(e, KeyEventType.UP);
    }

    public static class Builder {
        private final Map<Integer, UserInput> inputMap = new HashMap<>();

        public Builder() {

        }

        public Builder map(Integer keyEvent, UserInput userInput) {
            inputMap.put(keyEvent, userInput);
            return this;
        }

        public UserInputDefinition build() {
            return new UserInputDefinition(this);
        }
    }
}
