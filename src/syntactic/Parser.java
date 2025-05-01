package syntactic;

import lexical.Location;
import lexical.Tag;
import lexical.Token;
import syntactic.syntax.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

// This is a recursive descent parser
//      backtracking is implemented by branching the parser and then joining
//      the branches together. This is done to allow the parser to backtrack
//      whenever needed.
// Any function like `pull*()` Is idempotent on failure.


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

    public Token peek() {
        return tokens.get(index);
    }

    public Token consume() {
        final Token result = tokens.get(index);
        index += 1;
        return result;
    }
}
