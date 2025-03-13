package lexical;

import java.nio.CharBuffer;

public class Lexer {
    CharBuffer source;
    int index;

    public Lexer(CharBuffer source) {
        this.source = source;
        index = 0;
    }

    public boolean atEnd() {
        return index >= source.length();
    }

    private char consume() throws LexingError {
        if (atEnd()) throw new LexingError(Location.atIndex(source, index), "Unexpected EOI");
        final char ret = source.get(index);
        index += 1;
        return ret;
    }

    private char peek() throws LexingError {
        if (atEnd()) throw new LexingError(Location.atIndex(source, index),"Unexpected EOI");
        return source.get(index);
    }

    private void skipWhitespace() throws LexingError {
        while (" \t\n\r".indexOf(peek()) != -1) {
            consume();
        }
    }

    private Token consumeNumber() throws LexingError {
        assert Character.isDigit(peek());
        final int start = index;
        while (!atEnd() && Character.isDigit(peek())) {
            consume();
        };
        if (peek() == '.') {
            do {
                consume();
            } while ( !atEnd() && Character.isDigit(peek()) );
        }

        final int end = index;
        return new Token(Tag.Number, start, end);
    }
    private Token consumeIdentifier() throws LexingError {
        assert Character.isLetter(peek());

        final int start = index;
        do {
            consume();
        } while (!atEnd() && Character.isLetterOrDigit(peek()));
        final int end = index;
        return new Token(Tag.Identifier, start, end);
    }

    private Token consumeString() throws LexingError {
        assert peek() == '"';

        final int start = index;
        consume(); // consumes leading '"'
        while (peek() != '"') {
            final char head = consume();
            if (head == '\\') consume();
        }
        consume(); // consumes trailing '"'
        final int end = index;

        return new Token(Tag.String, start, end);
    }

    private Token consumeToken(Tag tag) throws LexingError {
        final int start = index;
        consume();
        final int end = index;
        return new Token(tag, start, end);
    }

    public Token pullToken() throws LexingError {
        skipWhitespace();
        if (Character.isDigit(peek())) {
            return consumeNumber();
        } else if (Character.isLetter(peek())) {
            final Token identifier = consumeIdentifier();
            return checkKeywords(identifier);
        }
        return switch (peek()) {
            case '(' -> consumeToken(Tag.OpenParenthesis);
            case ')' -> consumeToken(Tag.CloseParenthesis);
            case '{' -> consumeToken(Tag.OpenBrace);
            case '}' -> consumeToken(Tag.CloseBrace);
            case ';' -> consumeToken(Tag.Semicolon);
            case '+' -> consumeToken(Tag.Plus);
            case '-' -> consumeToken(Tag.Minus);
            case '*' -> consumeToken(Tag.Star);
            case '/' -> consumeToken(Tag.Slash);
            case '=','!','<','>' -> handleComparison();
            case '"' -> consumeString();

            default -> throw new LexingError(Location.atIndex(source,index),"unrecognised token at " + index);
        };
    }

    private Token checkKeywords(Token base) {
        final var identifier = source.subSequence(base.start(),base.end());
        if (identifier.compareTo(CharBuffer.wrap("fn")) == 0) {
            return new Token(Tag.FnKeyword, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("true")) == 0) {
            return new Token(Tag.True, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("false")) == 0) {
            return new Token(Tag.False, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("var")) == 0) {
            return new Token(Tag.VarKeyword, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("return")) == 0) {
            return new Token(Tag.ReturnKeyword, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("if")) == 0) {
            return new Token(Tag.IfKeyword, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("while")) == 0) {
            return new Token(Tag.WhileKeyword, base.start(),base.end());
        }
        if (identifier.compareTo(CharBuffer.wrap("else")) == 0) {
            return new Token(Tag.ElseKeyword, base.start(),base.end());
        }
        return base;
    }
    private Token handleComparison() throws LexingError {
        final int start = index;
        final char head = consume();
        final boolean has_tail = peek() == '=';

        // consuming the tail of the comparison
        if (has_tail) consume();
        final int end = index;

        return switch (head) {
            case '=' -> new Token(has_tail ? Tag.Equal : Tag.Assign, start, end);
            case '>' -> new Token(has_tail ? Tag.GreaterEqual : Tag.Greater, start, end);
            case '<' -> new Token(has_tail ? Tag.LesserEqual : Tag.Less, start, end);
            case '!' -> new Token(has_tail ? Tag.NotEqual : Tag.Not, start, end);
            default -> throw new LexingError(Location.atIndex(source, index),"Invalid comparison operator");
        };
    }
}
