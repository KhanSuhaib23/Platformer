package com.suhaib.game.render;

import com.suhaib.game.entity.mobs.Player;
import com.suhaib.game.graphics.Display;
import com.suhaib.game.level.Level;
import com.suhaib.game.level.tile.Tile;
import com.suhaib.game.math.RenderPosition;
import com.suhaib.game.math.TilePosition;

public class Renderer {
    private final Display display;

    public Renderer(Display display) {
        this.display = display;
    }

    public void render(Level level, RenderPosition renderPosition) {
        int xScroll = (int) renderPosition.x();
        int yScroll = (int) renderPosition.y();

        display.setOffSet(xScroll, yScroll);
        int x0 = xScroll / 16;
        int x1 = (xScroll + display.WIDTH) / 16;
        int y0 = yScroll / 16;
        int y1 = (yScroll + display.HEIGHT) / 16;
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                try {
                    renderTile(x * 16, y * 16, level.getTile(new TilePosition(x, y)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//
//        for (int y = 0; y < level.height(); y++) {
//            for (int x = 0; x < level.width(); x++) {
//                Tile tile = level.getTile(new TilePosition(x, y));
//                display.pixels[x + y * display.WIDTH] = tile.solid() ? 0 : 0xffffffff;
//            }
//        }
    }

    public void renderTile(int xPosition, int yPosition, Tile tile) {

        xPosition -= display.xOffSet;
        yPosition -= display.yOffSet;
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
}
