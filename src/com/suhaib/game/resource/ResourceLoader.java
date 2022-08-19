package com.suhaib.game.resource;

import java.util.Map;

public interface ResourceLoader<T> {
    T load(ResourceIndex.ResourceContext context, ResourceIndex index);
}
