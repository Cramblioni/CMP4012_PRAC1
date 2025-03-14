package syntactic;

import lexical.Location;
import lexical.Tag;
import lexical.Token;
import syntactic.syntax.*;

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
        final CharBuffer identifier = source.subSequence(head.start(), head.end());
        return Optional.of(new IdentifierNode(Location.atIndex(source, head.start()), identifier));
    };
    public Optional<AstNode> pullLocation() {
        // TODO: implement `head[index]` syntax
        Optional<IdentifierNode> head_opt = pullIdentifier();
        if (head_opt.isEmpty()) {
            return Optional.empty();
        }
        AstNode current = head_opt.get();
        while (peek().tag() == Tag.Dot) {
            Token dot = consume();
            if (peek().tag() != Tag.Identifier) {
                return Optional.empty();
            }
            Token field_token = consume();
            CharBuffer field = source.subSequence(field_token.start(), field_token.end());
            current = new AccessNode(Location.atIndex(source, dot.start()), current, field);
        }
        return Optional.of(current);
    }

    public Optional<AstNode> pullExpressionBase() {
        return pullLocation();
    }
    public Optional<AstNode> pullExpressionLow() {
        Optional<AstNode> head_opt = pullExpressionBase();
        if (head_opt.isEmpty()) return Optional.empty();
        final AstNode lhs = head_opt.get();
        
        Token operator = peek();
        if (operator.tag() != Tag.Star && operator.tag() != Tag.Slash) return Optional.of(lhs);
        consume();
        
        Optional<AstNode> rhs_opt = pullExpressionLow();
        if (rhs_opt.isEmpty()) return Optional.empty();
        
        Location location = Location.atIndex(source, operator.start());
        return Optional.of(new OperatorNode(location, operator.tag(), lhs, rhs_opt.get()));
    }
}
