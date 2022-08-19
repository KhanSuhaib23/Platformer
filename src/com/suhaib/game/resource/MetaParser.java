package com.suhaib.game.resource;

import java.io.BufferedReader;
import java.util.function.Function;

public class MetaParser {
    enum TokenType {
        IDENTIFIER,
        LEFT_BRACE,
        RIGHT_BRACE,
        COLON,
        INTEGER,
        FLOAT,
        STRING,
        BOOLEAN,
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

    private final char[] file;
    private int pos = 0;
    private int sz;



    public MetaParser(char[] content) {
        file = content;
        sz = content.length;
    }

    public Token expectToken(TokenType type) {
        Token token = nextToken();

        if (token.type() != type) {
            throw new RuntimeException("Encountered token type " + token.type() + " expected " + type);
        }

        return token;
    }

    public Token nextToken() {
        for (; pos < sz && Character.isWhitespace(file[pos]); ++pos);

        if (pos == sz) {
            return new SymbolToken(TokenType.EOF);
        }

        char ch = file[pos++];

        switch (ch) {
            case '{': return new SymbolToken(TokenType.LEFT_BRACE);
            case '}': return new SymbolToken(TokenType.RIGHT_BRACE);
            case ':': return new SymbolToken(TokenType.COLON);
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
                    while (isIdentifierCharacter.apply(ch = file[pos])) {
                        sb.append(ch);
                        pos++;
                    }

                    String s = sb.toString();

                    if (s.equals("true")) {
                        return new BooleanToken(true);
                    } else if (s.equals("false")) {
                        return new BooleanToken(false);
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
