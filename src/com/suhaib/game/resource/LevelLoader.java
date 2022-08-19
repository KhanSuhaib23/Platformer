package com.suhaib.game.resource;

import com.suhaib.game.level.Level;
import com.suhaib.game.math.TilePosition;

import static com.suhaib.game.resource.Constants.RESOURCE_BASE;

public class LevelLoader implements ResourceLoader<Level> {
    @Override
    public Level load(ResourceIndex.ResourceContext context, ResourceIndex index) {
        TileSet tileSet = index.load(TileSet.class, context.get("tileset"));
        int x = context.get("spawn_loc.x");
        int y = context.get("spawn_loc.y");
        TilePosition spawnLocation = new TilePosition(x, y);
        String path = RESOURCE_BASE + context.get("path");

        return new Level(path, tileSet, spawnLocation);
    }
}
