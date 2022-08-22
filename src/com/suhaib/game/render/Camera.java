package com.suhaib.game.render;

import com.suhaib.game.math.RenderPosition;

import java.util.function.UnaryOperator;

public class Camera {
    private final int width;
    private final int height;
    private final UnaryOperator<RenderPosition> renderPosMapper;
    private RenderPosition cameraPosition;

    public Camera(int width, int height, UnaryOperator<RenderPosition> renderPosMapper) {
        this.width = width;
        this.height = height;
        this.renderPosMapper = renderPosMapper;
    }

    public void setCameraPosition(RenderPosition cameraFollow) {
        RenderPosition modifiedCameraFollow = renderPosMapper.apply(cameraFollow);
        cameraPosition = new RenderPosition(Math.max(modifiedCameraFollow.x() - width / 2, 0), Math.max(modifiedCameraFollow.y() - height / 2, 0));
    }

    public RenderPosition getCameraLocalPosition(RenderPosition worldPosition) {
        return new RenderPosition(worldPosition.x() - cameraPosition.x(), worldPosition.y() - cameraPosition.y());
    }

    public RenderPosition cameraPosition() {
        return cameraPosition;
    }

//
//    public RenderPosition getCameraTopLeft(RenderPosition worldPosition) {
//        return new RenderPosition(worldPosition.x() - width / 2, worldPosition.y() - height / 2);
//    }

    public RenderPosition getCameraTopLeft(RenderPosition worldPosition) {
        RenderPosition renderPosition = renderPosMapper.apply(worldPosition);
        return new RenderPosition(Math.max(renderPosition.x() - width / 2, 0), Math.max(renderPosition.y() - height / 2, 0));
    }
}
