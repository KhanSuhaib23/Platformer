package com.suhaib.game.render;

import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.Vector2;

import java.util.function.UnaryOperator;

public class Camera {
    private final Vector2 frameDimension;
    private final Vector2 halfFrameDimension;
    private final UnaryOperator<Vector2> cameraPositionMapper;
    private Vector2 cameraPosition;

    public Camera(Vector2 frameDimension, UnaryOperator<Vector2> cameraPositionMapper) {
        this.frameDimension = frameDimension;
        this.halfFrameDimension = new Vector2(frameDimension.x / 2, frameDimension.y / 2);
        this.cameraPositionMapper = cameraPositionMapper;
    }

    // TODO(suhaibnk): reintroduce some way to modify camera position relative to player position
    public void setCameraFollow(Vector2 cameraFollow) {
        cameraPosition = Vector2.bound(Vector2.sub(cameraFollow, halfFrameDimension));
    }

    public Vector2 getCameraLocalPosition(Vector2 worldPosition) {
        return Vector2.sub(worldPosition, cameraPosition);
    }

    public Vector2 cameraPosition() {
        return cameraPosition;
    }

    public RenderPosition cameraRenderPosition() {
        return cameraPosition.renderPosition();
    }
}
