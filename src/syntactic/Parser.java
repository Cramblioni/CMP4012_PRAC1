package syntactic;

import lexical.Location;
import lexical.Tag;
import lexical.Token;
import syntactic.syntax.IdentifierNode;
import syntactic.syntax.NumberNode;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Optional;

public class Parser {
    CharBuffer source;
    ArrayList<Token> tokens;
    int index;

    public Parser branch() {
        Parser branch = new Parser();
        branch.tokens = tokens;
        branch.index = index;
        return branch;
    }
    public void join(Parser branch) {
        index = Math.max(index, branch.index);
    }

    public Token peek() {
        return tokens.get(index);
    }
    public Token consume() {
        final Token result = tokens.get(index);
        index += 1;
        return result;
    }

    public Optional<NumberNode> pullNumber() {
        final Token head = peek();
        if (head.tag() != Tag.Number) return Optional.empty();
        float value;
        try {
            value = Float.parseFloat(source.subSequence(head.start(), head.end()).toString());
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.of(new NumberNode(Location.atIndex(source, head.start()), value));
    };
    public Optional<IdentifierNode> pullIdentifier() {
        final Token head = peek();
        if (head.tag() != Tag.Identifier) return Optional.empty();
        CharBuffer identifier = source.subSequence(head.start(), head.end());
        return Optional.of(new IdentifierNode(Location.atIndex(source, head.start()), identifier));
    };
}
