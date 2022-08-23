package com.suhaib.game.resource;

import java.util.function.Function;

public class TileSetLoader implements ResourceLoader<TileSet> {
    @Override
    public TileSet load(MetaParser.Node context, ResourceIndex index) {
        String path = Constants.RESOURCE_BASE + context.get("path").value();
        int tileWidth = context.get("tile")
                .get("width")
                .value();

        int tileHeight = context.get("tile")
                .get("height")
                .value();

        // TODO(suhaibnk): we now support array so maybe this isn't needed
        Function<Integer, Boolean> isSolid = i -> context.get("solid").get(i).value();

        return new TileSet(path, tileWidth, tileHeight, isSolid);
    }
}
