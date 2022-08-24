package com.suhaib.game.resource;

import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.graphics.sprite.SpriteSheet;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpriteSheetLoader implements ResourceLoader<SpriteSheet> {
    @Override
    public SpriteSheet load(MetaParser.Node context, ResourceIndex index) {
        String path = Constants.RESOURCE_BASE + context.get("path").value();
        int width = context.get("sprite").get("width").value();
        int height = context.get("sprite").get("height").value();

        Map<String, Sprite.Definition> spriteDefinitions = context.get("sprites").stream()
                .map(o -> (Map.Entry<String, MetaParser.Node>) o)
                .map(e -> Map.entry(e.getKey(),
                        new Sprite.Definition(e.getValue().get("x").value(), e.getValue().get("y").value())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new SpriteSheet(path, width, height, spriteDefinitions);
    }
}
