package com.suhaib.game.resource;

import com.suhaib.game.graphics.Animation;
import com.suhaib.game.graphics.sprite.Sprite;
import com.suhaib.game.graphics.sprite.SpriteSheet;

import java.util.List;
import java.util.stream.Collectors;

public class AnimationLoader implements ResourceLoader<Animation> {
    @Override
    public Animation load(MetaParser.Node context, ResourceIndex index) {
        SpriteSheet spriteSheet = index.load(SpriteSheet.class, context.get("spritesheet").value());
        List<Sprite> right = context.get("sprites").get("right").stream()
                .map(o -> (MetaParser.Node) o)
                .map(n -> spriteSheet.get(n.value()))
                .collect(Collectors.toList());

        List<Sprite> left = context.get("sprites").get("left").stream()
                .map(o -> (MetaParser.Node) o)
                .map(n -> spriteSheet.get(n.value()))
                .collect(Collectors.toList());

        return new Animation(left, right);

    }
}
