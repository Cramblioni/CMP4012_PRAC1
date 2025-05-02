package syntactic;

import lexical.Location;
import lexical.Tag;
import lexical.Token;
import syntactic.syntax.*;

import java.nio.CharBuffer;
import java.util.ArrayList;

// This is a recursive descent parser
//      backtracking is implemented by branching the parser and then joining
//      the branches together. This is done to allow the parser to backtrack
//      whenever needed.
// Any function like `pull*()` IS idempotent on failure.


public class Parser {
    CharBuffer source;
    ArrayList<Token> tokens;
    int index;

    // Creates a copy of the current parser state.
    public Parser branch() {
        Parser branch = new Parser();
        branch.tokens = tokens;
        branch.index = index;
        return branch;
    }
    // updates the current parser state to that of a branched parser.
    public void join(Parser branch) {
        index = Math.max(index, branch.index);
    }

    private Boolean atEnd() {return index >= tokens.size();}

    public Token peek() {
        return tokens.get(index);
    }

    public Token consume() {
        final Token result = tokens.get(index);
        index += 1;
        return result;
    }

    private void noEOF() throws ParserError {
        if (atEnd()) { return; }
        throw new ParserError(
                Location.atIndex(source, source.length()),
                "Unexpected EOF"
        );
    }

    public NumberNode pullNumber() throws ParserError {
        noEOF();
        final Location loc = Location.atIndex(source, peek().start());
        if (peek().tag() != Tag.Number) {
            throw new ParserError(loc,"Expected number literal");
        }
        final Token tok = consume();
        final float value = Float.parseFloat(
                source.subSequence(tok.start(), tok.end()).toString()
        );
        return new NumberNode(loc, value);
    }
    public StringNode pullString() throws ParserError {
        noEOF();
        final Location loc = Location.atIndex(source, peek().start());
        if (peek().tag() != Tag.String) {
            throw new ParserError(loc,"Expected string literal");
        }
        final Token tok = consume();
        return new StringNode(loc,
                source.subSequence(tok.start() + 1, tok.end() - 1).toString()
        );
    }
    public AstNode pullLiteral() throws ParserError {
        ParserError error = null;

        try {return pullNumber();}
        catch (ParserError e) {error = e;}
        try {return pullString();}
        catch (ParserError e) {
            assert true; // Exception gets re-raised later
        }

        throw  new ParserError(
                error.pos,
                "Expected literal (either number or string)"
        );
    }

    public IdentifierNode pullIdentifier() throws ParserError {
        noEOF();
        final Location loc = Location.atIndex(source, peek().start());
        if (peek().tag() != Tag.Number) {
            throw new ParserError(loc,"Expected identifier");
        }
        final Token tok = consume();
        return new IdentifierNode(loc, source.subSequence(tok.start(), tok.end()));
    }

    public AstNode pullLocation() throws ParserError {
        // TODO: Indexing
        // TODO: Accessing
        return pullIdentifier();
    }

    public AstNode pullExpression4() throws ParserError {
        ParserError accumulator = null;

        // TODO: Invocation
        try { return pullLocation(); }
        catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        try { return pullLiteral(); }
        catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        // TODO: Paren
        // TODO: Negation
        if (peek().tag() == Tag.Not) {
            Parser branch = branch();
            branch.consume();
            try {
                AstNode arg = branch.pullExpression4();
            } catch (ParserError e) {
                accumulator = ParserError.accumulate(accumulator, e);
            }
        }
        assert accumulator != null;
        throw accumulator;
    }
}
