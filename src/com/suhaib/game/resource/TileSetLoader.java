package com.suhaib.game.resource;

import java.util.function.Function;

public class TileSetLoader implements ResourceLoader<TileSet> {
    @Override
    public TileSet load(ResourceIndex.ResourceContext context, ResourceIndex index) {
        String path = Constants.RESOURCE_BASE + context.get("path");
        int tileWidth = context.get("tile.width");
        int tileHeight = context.get("tile.height");

        Function<Integer, Boolean> isSolid = i -> context.get("tile." + i + ".solid");

        return new TileSet(path, tileWidth, tileHeight, isSolid);
    }
}
