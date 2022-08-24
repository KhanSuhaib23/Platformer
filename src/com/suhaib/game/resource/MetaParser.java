package com.suhaib.game.resource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetaParser {
    enum TokenType {
        IDENTIFIER,
        COLON,
        ASSIGN,
        ARRAY,
        OBJECT,
        INTEGER,
        FLOAT,
        STRING,
        BOOLEAN,
        END,
        EOF
    }

    public interface Token {
        TokenType type();
    }

    public static class IdentifierToken implements Token {
        private final String val;

        public IdentifierToken(String val) {
            this.val = val;
        }

        public TokenType type() {
            return TokenType.IDENTIFIER;
        }

        public String val() {
            return val;
        }
    }

    public static class SymbolToken implements Token {
        private final TokenType type;

        public SymbolToken(TokenType type) {
            this.type = type;
        }

        public TokenType type() {
            return type;
        }
    }

    public static class IntegerToken implements Token {
        private final int val;

        public IntegerToken(int val) {
            this.val = val;
        }

        public TokenType type() {
            return TokenType.INTEGER;
        }

        public int val() {
            return val;
        }
    }

    public static class StringToken implements Token {
        private final String val;

        public StringToken(String val) {
            this.val = val;
        }

        public TokenType type() {
            return TokenType.STRING;
        }

        public String val() {
            return val;
        }
    }

    public static class FloatToken implements Token {
        private final float val;

        public FloatToken(float val) {
            this.val = val;
        }

        public TokenType type() {
            return TokenType.FLOAT;
        }

        public float val() {
            return val;
        }
    }

    public static class BooleanToken implements Token {
        private final boolean val;

        public BooleanToken(boolean val) {
            this.val = val;
        }

        public TokenType type() {
            return TokenType.BOOLEAN;
        }

        public boolean val() {
            return val;
        }
    }

    private enum ResourceValueType {
        INTEGER,
        FLOAT,
        BOOLEAN,
        STRING,
        IDENTIFIER
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

    private static class IdentifierValue implements ResourceValue<String> {
        private final String val;

        public IdentifierValue(String val) {
            this.val = val;
        }

        @Override
        public String val() {
            return val;
        }

        @Override
        public ResourceValueType type() {
            return ResourceValueType.IDENTIFIER;
        }
    }

    public record DefinitionKey(String name, String type) {

    }

    public enum NodeType {
        VALUE,
        OBJECT,
        ARRAY
    }

    public interface Node {
        NodeType type();

        Node get(String key);
        Node get(int index);
        <T> T value();
        <V> Stream<V> stream();
        int size();
    }

    public static class ValueNode<T> implements Node {
        private final ResourceValue<T> resourceValue;

        public ValueNode(ResourceValue<T> resourceValue) {
            this.resourceValue = resourceValue;
        }

        @Override
        public NodeType type() {
            return NodeType.VALUE;
        }

        @Override
        public Node get(String key) {
            throw new RuntimeException("Cannot get by key on a value node");
        }

        @Override
        public Node get(int index) {
            throw new RuntimeException("Cannot get by index on a value node");
        }

        @Override
        public T value() {
            return resourceValue.val();
        }

        @Override
        public Stream<T> stream() {
            return Stream.of(resourceValue.val());
        }

        @Override
        public int size() {
            return 1;
        }
    }

    public static class ObjectNode implements Node {
        private final Map<String, Node> objectMap;

        public ObjectNode(Map<String, Node> objectMap) {
            this.objectMap = objectMap;
        }

        @Override
        public NodeType type() {
            return NodeType.OBJECT;
        }

        @Override
        public Node get(String key) {
            return Optional.ofNullable(objectMap.get(key)).orElseThrow(
                    () -> new RuntimeException("Key " + key + " doesn't exists on this node."));
        }

        @Override
        public Node get(int index) {
            throw new RuntimeException("Cannot get by index on a Object node");
        }

        @Override
        public <T> T value() {
            throw new RuntimeException("Cannot get by index on a object node");
        }

        @Override
        public Stream<Map.Entry<String, Node>> stream() {
            return objectMap.entrySet().stream();
        }

        @Override
        public int size() {
            return objectMap.size();
        }
    }

    public static class ArrayNode implements Node {
        private final List<Node> arrayList;

        public ArrayNode(List<Node> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public NodeType type() {
            return NodeType.ARRAY;
        }

        @Override
        public Node get(String key) {
            throw new RuntimeException("Cannot get by key on a Array node");
        }

        @Override
        public Node get(int index) {
            if (index < arrayList.size()) {
                return arrayList.get(index);
            } else {
                throw new RuntimeException("Index " + index + " out of range");
            }
        }

        @Override
        public <T> T value() {
            throw new RuntimeException("Cannot get by index on a Array node");
        }

        @Override
        public Stream<Node> stream() {
            return arrayList.stream();
        }

        @Override
        public int size() {
            return arrayList.size();
        }
    }

    private final char[] file;
    private int pos = 0;
    private int sz;
    private Stack<Token> tokenStack = new Stack<>();

    public static class Definition {
        private final Map<String, Node> definitions;

        public Definition(Map<String, Node> definitions) {
            this.definitions = definitions;
        }

    }

    public MetaParser(char[] content) {
        file = content;
        sz = content.length;
    }


    public Token expectToken(TokenType type) {
        Token token = nextToken();

        ensure(token, type);

        return token;
    }

    public Map<DefinitionKey, Node> parse() {
        Map<DefinitionKey, Node> definitions = new HashMap<>();

        Token token;

        while ((token = nextToken()).type() != TokenType.EOF) {
            ensure(token, TokenType.IDENTIFIER);

            String name = tokenOfType(token, IdentifierToken.class).val();

            expectToken(TokenType.COLON);

            token = expectToken(TokenType.IDENTIFIER);

            String type = tokenOfType(token, IdentifierToken.class).val();

            DefinitionKey key = new DefinitionKey(name, type);

            Map<String, Node> innerDefinition = new HashMap<>();

            while ((token = nextToken()).type() != TokenType.EOF) {
                if (token.type() == TokenType.END) break;

                ensure(token, TokenType.IDENTIFIER);

                String iName = tokenOfType(token, IdentifierToken.class).val();

                expectToken(TokenType.ASSIGN);

                Node node = parseNode();

                innerDefinition.put(iName, node);
            }

            definitions.put(key, new ObjectNode(innerDefinition));
        }



        return definitions;
    }

    ObjectNode parseObject() {
        Map<String, Node> nodes = new HashMap<>();
        Token token;

        while ((token = nextToken()).type() == TokenType.OBJECT) {
            token = expectToken(TokenType.IDENTIFIER);
            String name = tokenOfType(token, IdentifierToken.class).val();
            expectToken(TokenType.ASSIGN);
            Node node = parseNode();

            nodes.put(name, node);
        }

        ensure(token, TokenType.END);

        return new ObjectNode(nodes);
    }

    ArrayNode parseArray() {
        List<Node> nodes = new ArrayList<>();
        Token token;

        while ((token = nextToken()).type() == TokenType.ARRAY) {
            Node node = parseNode();

            nodes.add(node);
        }

        ensure(token, TokenType.END);

        return new ArrayNode(nodes);
    }

    ValueNode parseValue() {
        Token token = nextToken();

        ResourceValue value = switch (token.type()) {
            case IDENTIFIER -> new IdentifierValue(((MetaParser.IdentifierToken) token).val());
            case INTEGER -> new IntegerValue(((MetaParser.IntegerToken) token).val());
            case STRING -> new StringValue(((MetaParser.StringToken) token).val());
            case FLOAT -> new FloatValue(((MetaParser.FloatToken) token).val());
            case BOOLEAN -> new BooleanValue(((MetaParser.BooleanToken) token).val());
            default -> {
                ensure(token, TokenType.IDENTIFIER, TokenType.INTEGER, TokenType.STRING, TokenType.FLOAT, TokenType.BOOLEAN);
                throw new RuntimeException();
            }
        };

        return new ValueNode(value);
    }

    Node parseNode() {
        Token token = nextToken();
        pushToken(token);

        if (token.type() == TokenType.OBJECT) {
            return parseObject();
        } else if (token.type() == TokenType.ARRAY) {
            return parseArray();
        } else {
            return parseValue();
        }
    }

    public void error(Token got, TokenType ...expected) {
        throw new RuntimeException("[ERROR]: Parse error at pos " + pos +
                ". 'Expected one of " +
                Arrays.stream(expected).map(Enum::name).collect(Collectors.joining("[", ", ", "]")) +
                " but got " + got.type() + ".");
    }

    public void ensure(Token token, TokenType ...type) {
        if (!Arrays.stream(type).anyMatch(t -> t == token.type())) {
            throw new RuntimeException("[ERROR]: Parse error at pos " + pos +
                    ". 'Expected token " + Arrays.stream(type).map(Enum::name).collect(Collectors.joining("[", ", ", "]")) + " but got " + token.type() + ".");
        }
    }

    public static <T extends Token> T tokenOfType(Token token, Class<T> clazz) {
        return (T) token;
    }

    public void pushToken(Token token) {
        tokenStack.push(token);
    }

    public Token nextToken() {
        if (!tokenStack.empty()) return tokenStack.pop();

        for (; pos < sz && Character.isWhitespace(file[pos]); ++pos);

        if (pos == sz) {
            return new SymbolToken(TokenType.EOF);
        }

        char ch = file[pos++];

        switch (ch) {
            case ':': return new SymbolToken(TokenType.COLON);
            case '=': return new SymbolToken(TokenType.ASSIGN);
            case '-': return new SymbolToken(TokenType.ARRAY);
            case '+': return new SymbolToken(TokenType.OBJECT);
            case '\'': {
                StringBuilder sb = new StringBuilder();
                while ((ch = file[pos++]) != '\'') {
                    sb.append(ch);
                }
                return new StringToken(sb.toString());
            }
            default: {

                StringBuilder sb = new StringBuilder();

                sb.append(ch);

                Function<Character, Boolean> isIdentifierCharacter = c -> Character.isAlphabetic(c) || Character.isDigit(c) || c == '.' || c == '_';

                if (Character.isAlphabetic(ch)) {
                    while (pos < sz  && isIdentifierCharacter.apply(ch = file[pos])) {
                        sb.append(ch);
                        pos++;
                    }

                    String s = sb.toString();

                    if (s.equals("true")) {
                        return new BooleanToken(true);
                    } else if (s.equals("false")) {
                        return new BooleanToken(false);
                    } else if (s.equals("end")) {
                        return new SymbolToken(TokenType.END);
                    }

                    return new IdentifierToken(sb.toString());
                } else if (Character.isDigit(ch)) {
                    while (Character.isDigit(ch = file[pos])) {
                        sb.append(ch);
                        pos++;
                    }

                    if (ch == '.') {
                        pos++;

                        while (Character.isDigit(ch = file[pos])) {
                            sb.append(ch);
                            pos++;
                        }

                        return new FloatToken(Float.parseFloat(sb.toString()));
                    }

                    return new IntegerToken(Integer.parseInt(sb.toString()));
                }

                throw new RuntimeException("Unknown start encountered " + ch);
            }
        }
    }
}
