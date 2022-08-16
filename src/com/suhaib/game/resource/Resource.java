package com.suhaib.game.resource;

import java.io.Serializable;

public interface Resource<T> extends Serializable {
    long id();
    T load();
    void unload();
}
