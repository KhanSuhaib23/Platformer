package com.suhaib.game.resource;

import com.suhaib.game.level.tile.Tile;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<Tile.Definition> tileDefinitions = IntStream.range(0, context.get("tiles").size())
                .mapToObj(i -> context.get("tiles").get(i))
                .map(n -> new Tile.Definition(n.get("x").value(), n.get("y").value(), n.get("solid").value()))
                .collect(Collectors.toList());

        return new TileSet(path, tileWidth, tileHeight, tileDefinitions);
    }
}
