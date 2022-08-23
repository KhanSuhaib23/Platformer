package com.suhaib.game.resource;

import com.suhaib.game.level.Level;
import com.suhaib.game.math.TilePosition;

import static com.suhaib.game.resource.Constants.RESOURCE_BASE;

public class LevelLoader implements ResourceLoader<Level> {
    @Override
    public Level load(MetaParser.Node context, ResourceIndex index) {
        TileSet tileSet = index.load(TileSet.class, context.get("tileset").value());
        int x = context.get("player_spawn").get("x").value();
        int y = context.get("player_spawn").get("y").value();

        TilePosition spawnLocation = new TilePosition(x, y);
        String path = RESOURCE_BASE + context.get("path").value();

        return new Level(path, tileSet, spawnLocation);
    }
}
