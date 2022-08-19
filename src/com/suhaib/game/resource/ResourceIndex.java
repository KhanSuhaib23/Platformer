package com.suhaib.game.resource;


import com.suhaib.game.level.Level;
import com.suhaib.game.level.tile.Tile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ResourceIndex {
    private enum ResourceValueType {
        INTEGER,
        FLOAT,
        BOOLEAN,
        STRING,
        REFERENCE
    }

    public class ResourceContext {
        private final Map<String, ResourceValue> valueMap;

        public ResourceContext(Map<String, ResourceValue> valueMap) {
            this.valueMap = valueMap;
        }

        public <T> T get(String key) {
            return (T) Optional.ofNullable(valueMap.get(key)).map(rv -> rv.val()).orElseThrow(() -> new RuntimeException("No value for key " + key));
        }
    }

    public interface ResourceValue<T> {
        ResourceValueType type();
        T val();
    }

    private static class IntegerValue implements ResourceValue<Integer> {
        private final int val;

        public IntegerValue(int val) {
            this.val = val;
        }

        @Override
        public Integer val() {
            return val;
        }

        @Override
        public ResourceValueType type() {
            return ResourceValueType.INTEGER;
        }
    }

    private static class StringValue implements ResourceValue<String> {
        private final String val;

        public StringValue(String val) {
            this.val = val;
        }

        @Override
        public String val() {
            return val;
        }

        @Override
        public ResourceValueType type() {
            return ResourceValueType.STRING;
        }
    }

    private static class FloatValue implements ResourceValue<Float> {
        private final float val;

        public FloatValue(float val) {
            this.val = val;
        }

        @Override
        public Float val() {
            return val;
        }

        @Override
        public ResourceValueType type() {
            return ResourceValueType.FLOAT;
        }
    }

    private static class BooleanValue implements ResourceValue<Boolean> {
        private final boolean val;

        public BooleanValue(boolean val) {
            this.val = val;
        }

        @Override
        public Boolean val() {
            return val;
        }

        @Override
        public ResourceValueType type() {
            return ResourceValueType.BOOLEAN;
        }
    }

    private static class ReferenceValue implements ResourceValue<String> {
        private final String val;

        public ReferenceValue(String val) {
            this.val = val;
        }

        @Override
        public String val() {
            return val;
        }

        @Override
        public ResourceValueType type() {
            return ResourceValueType.REFERENCE;
        }
    }

    private record ResourceKey(Class clazz, String name) {

    }

    private record ResourceLocator() {

    }

    private final Map<ResourceKey, ResourceContext> resourceIndex;
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
            resourceIndex = new HashMap<>();
            char[] content = new char[(int)file.length()];

            reader.read(content, 0, (int) file.length());

            MetaParser parser = new MetaParser(content);

            MetaParser.Token token;

            while ((token = parser.nextToken()).type() != MetaParser.TokenType.EOF) {
                if (token.type() != MetaParser.TokenType.IDENTIFIER) {
                    throw new RuntimeException("Expecting either EOF or identifier got " + token.type());
                }

                String name = ((MetaParser.IdentifierToken) token).val();
                parser.expectToken(MetaParser.TokenType.COLON);
                token = parser.expectToken(MetaParser.TokenType.IDENTIFIER);
                String className = ((MetaParser.IdentifierToken) token).val();

                ResourceKey resourceKey = new ResourceKey(classMap.get(className), name);

                parser.expectToken(MetaParser.TokenType.LEFT_BRACE);

                Map<String, ResourceValue> valueMap = new HashMap<>();

                while ((token = parser.nextToken()).type() != MetaParser.TokenType.RIGHT_BRACE) {
                    if (token.type() != MetaParser.TokenType.IDENTIFIER) {
                        throw new RuntimeException("Expected identifier got " + token.type());
                    }

                    String varName = ((MetaParser.IdentifierToken) token).val();
                    parser.expectToken(MetaParser.TokenType.COLON);
                    token = parser.nextToken();

                    ResourceValue value = switch (token.type()) {
                        case IDENTIFIER -> new ReferenceValue(((MetaParser.IdentifierToken) token).val());
                        case INTEGER -> new IntegerValue(((MetaParser.IntegerToken) token).val());
                        case STRING -> new StringValue(((MetaParser.StringToken) token).val());
                        case FLOAT -> new FloatValue(((MetaParser.FloatToken) token).val());
                        case BOOLEAN -> new BooleanValue(((MetaParser.BooleanToken) token).val());
                        default -> throw new RuntimeException("Expected some value type got " + token.type());
                    };

                    valueMap.put(varName, value);

                    resourceIndex.put(resourceKey, new ResourceContext(valueMap));
                }
            }

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
