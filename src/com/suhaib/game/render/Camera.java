package com.suhaib.game.render;

import com.suhaib.game.math.RenderBound;
import com.suhaib.game.math.RenderPosition;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Camera {
    private final int width;
    private final int height;
    private final UnaryOperator<RenderPosition> renderPosMapper;

    public Camera(int width, int height, UnaryOperator<RenderPosition> renderPosMapper) {
        this.width = width;
        this.height = height;
        this.renderPosMapper = renderPosMapper;
    }

    public RenderPosition getPosition(RenderPosition cameraCenter, RenderPosition worldPosition) {
        RenderPosition cameraPosition = new RenderPosition(cameraCenter.x() - width / 2, cameraCenter.y() - height / 2);

        return new RenderPosition(worldPosition.x() - cameraPosition.x(), worldPosition.y() - cameraPosition.y());
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
