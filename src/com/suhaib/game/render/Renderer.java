package com.suhaib.game.render;

import com.suhaib.game.entity.mobs.Player;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.level.Level;
import com.suhaib.game.level.tile.Tile;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.TilePosition;
import com.suhaib.game.math.Vector2;

public class Renderer {
    private final Display display;
    private final Camera camera;

    public Renderer(Display display, Camera camera) {
        this.display = display;
        this.camera = camera;
    }

    public void render(Level level) {
        int xScroll = (int) camera.cameraPosition().renderPosition().x();
        int yScroll = (int) camera.cameraPosition().renderPosition().y();

        int x0 = xScroll / 16;
        int x1 = (xScroll + display.WIDTH) / 16;
        int y0 = yScroll / 16;
        int y1 = (yScroll + display.HEIGHT) / 16;

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                try {
                    renderTile(x * 16 - xScroll, y * 16 - yScroll, level.getTile(new TilePosition(x, y)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void renderTile(int xPosition, int yPosition, Tile tile) {
        int xAbsolute;
        int yAbsolute;
        for (int y = 0; y < tile.height(); y++) {
            yAbsolute = y + yPosition;
            if (yAbsolute < -16 || yAbsolute >= display.HEIGHT) break;
            if (yAbsolute < 0) yAbsolute = 0;
            for (int x = 0; x < tile.width(); x++) {
                xAbsolute = x + xPosition;
                if (xAbsolute < -16 || xAbsolute >= display.WIDTH) break;
                if (xAbsolute < 0) xAbsolute = 0;
                display.pixels[xAbsolute + yAbsolute * display.WIDTH] = tile.pixel(x, y);
            }
        }
    }

    public void renderSprite(Vector2 position, Sprite sprite) {
        RenderPosition renderPosition = Vector2.sub(position, camera.cameraPosition()).renderPosition();
        long xPosition = renderPosition.x();
        long yPosition = renderPosition.y();
        long xAbsolute;
        long yAbsolute;
        for (int y = 0; y < sprite.size; y++) {
            yAbsolute = y + yPosition;
            if (yAbsolute < -16 || yAbsolute >= display.HEIGHT) break;
            if (yAbsolute < 0) yAbsolute = 0;
            for (int x = 0; x < sprite.size; x++) {
                xAbsolute = x + xPosition;
                if (xAbsolute < -16 || xAbsolute >= display.WIDTH) break;
                if (xAbsolute < 0) xAbsolute = 0;
                int color = sprite.pixels[x + y * sprite.size];
                if (color != 0xffff00ff) display.pixels[(int) (xAbsolute + yAbsolute * display.WIDTH)] = color;
            }
        }
    }
}
