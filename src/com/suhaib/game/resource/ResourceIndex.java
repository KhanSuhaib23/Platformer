package com.suhaib.game.resource;


import com.suhaib.game.level.Level;
import com.suhaib.game.level.tile.Tile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceIndex {

    private record ResourceKey(Class clazz, String name) {

    }

    private record ResourceLocator() {

    }

    private final Map<ResourceKey, MetaParser.Node> resourceIndex;
    private final Map<Class, ResourceLoader> resourceLoaders;

    public ResourceIndex(Builder builder) {
        try {
            Map<String, Class> classMap = new HashMap<>();
            this.resourceLoaders = builder.loaderMap;

            builder.loaderMap.entrySet().stream()
                    .forEach(e -> {
                        classMap.put(e.getKey().getSimpleName(), e.getKey());
                    });

            File file = new File(builder.metaPath);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            char[] content = new char[(int)file.length()];

            reader.read(content, 0, (int) file.length());

            MetaParser parser = new MetaParser(content);

            Map<MetaParser.DefinitionKey, MetaParser.Node> index = parser.parse();

            resourceIndex = index.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> new ResourceKey(classMap.get(e.getKey().type()), e.getKey().name()),
                            e -> e.getValue()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T load(Class<T> clazz, String name) {
        return (T) resourceLoaders.get(clazz).load(resourceIndex.get(new ResourceKey(clazz, name)), this);
    }

    public static Builder builder(String metaPath) {
        return new Builder(metaPath);
    }

    public static class Builder {
        private Map<Class, ResourceLoader> loaderMap = new HashMap<>();
        private String metaPath;

        public Builder(String metaPath) {
            this.metaPath = metaPath;
        }

        public <T> Builder loader(Class<T> clazz, ResourceLoader<T> loader) {
            loaderMap.put(clazz, loader);
            return this;
        }

        public ResourceIndex build() {
            return new ResourceIndex(this);
        }
    }
}
