package com.suhaib.game.resource;

import java.util.Map;

public interface ResourceLoader<T> {
    T load(MetaParser.Node context, ResourceIndex index);
}
