package syntactic;

import lexical.Location;
import lexical.Tag;
import lexical.Token;
import syntactic.syntax.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;

// This is a recursive descent parser
//      backtracking is implemented by branching the parser and then joining
//      the branches together. This is done to allow the parser to backtrack
//      whenever needed.
// Any function like `pull*()` IS idempotent on failure.


public class Parser {
    CharBuffer source;
    ArrayList<Token> tokens;
    int index;

    public Parser(CharBuffer source, ArrayList<Token> tokens, int index) {
        this.source = source;
        this.tokens = tokens;
        this.index = index;
    }

    // Creates a copy of the current parser state.
    public Parser branch() {
        return new Parser(source, tokens, index);
    }
    // updates the current parser state to that of a branched parser.
    public void join(Parser branch) {
        index = Math.max(index, branch.index);
    }

    public Boolean atEnd() {return index >= tokens.size();}

    public Token peek() throws ParserError {
        noEOF();
        return tokens.get(index);
    }

    public Token consume() throws ParserError {
        noEOF();
        final Token result = tokens.get(index);
        index += 1;
        return result;
    }

    private void noEOF() throws ParserError {
        if (!atEnd()) { return; }
        throw new ParserError(
                Location.atIndex(source, source.length()),
                "Unexpected EOF"
        );
    }

    public NumberNode pullNumber() throws ParserError {
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
        final Location loc = Location.atIndex(source, peek().start());
        if (peek().tag() != Tag.String) {
            throw new ParserError(loc,"Expected string literal");
        }
        final Token tok = consume();
        return new StringNode(loc,
                source.subSequence(tok.start() + 1, tok.end() - 1).toString()
        );
    }
    public BooleanNode pullBoolean() throws ParserError {
        final Token literal = peek();
        if (literal.tag() != Tag.True && literal.tag() != Tag.False) {
            throw new ParserError(
                    Location.atIndex(source, literal.start()),
                    "Expected either `true`/`false`"
            );
        }
        final boolean value = literal.tag() == Tag.True;
        consume();
        return new BooleanNode(
                Location.atIndex(source, literal.start()),
                value
        );
    }
    public AstNode pullLiteral() throws ParserError {
        ParserError accumulator = null;

        try {return pullNumber();}
        catch (ParserError e) {accumulator = ParserError.accumulate(accumulator, e);}
        try {return pullString();}
        catch (ParserError e) {accumulator = ParserError.accumulate(accumulator, e);}
        try {return pullBoolean();}
        catch (ParserError e) {accumulator = ParserError.accumulate(accumulator, e);}

        throw  new ParserError(
                accumulator.pos,
                "Expected literal (either number or string or boolean)"
        );
    }

    public IdentifierNode pullIdentifier() throws ParserError {
        final Location loc = Location.atIndex(source, peek().start());
        if (peek().tag() != Tag.Number) {
            throw new ParserError(loc,"Expected identifier");
        }
        final Token tok = consume();
        return new IdentifierNode(loc, source.subSequence(tok.start(), tok.end()));
    }

    // Expressions and Expression Components
    public AstNode pullLocation() throws ParserError {
        Parser branch = branch();
        AstNode accumulator = branch.pullIdentifier();
        while (branch.peek().tag() == Tag.Dot) {
            Token dot = consume();
            accumulator = new BinaryOperatorNode(
                    Location.atIndex(source, dot.start()),
                    dot.tag(),
                    accumulator,
                    pullIdentifier()
            );
        }
        join(branch);
        return accumulator;
    }

    private boolean shouldContinueFuncArgs() throws ParserError {
        if (peek().tag() == Tag.Comma) {
            consume();
            return true;
        }
        return false;
    }

    public AstNode pullExpression4() throws ParserError {
        ParserError accumulator = null;

        try {
            Parser branch = branch();
            AstNode location = branch.pullLocation();
            if (branch.peek().tag() != Tag.OpenParenthesis) {
                join(branch);
                return location;
            }
            // prepare for hell :)
            final Token openParen = branch.consume();
            ArrayList<AstNode> arguments = new ArrayList<AstNode>();
            do {
                if (branch.peek().tag() == Tag.CloseParenthesis) {
                    break;
                }
                arguments.add(branch.pullExpression());
            } while (shouldContinueFuncArgs());
            if (branch.peek().tag() != Tag.CloseParenthesis) {
                throw new ParserError(
                        Location.atIndex(source, branch.peek().start()),
                        "Expected `)` after function arguments"
                );
            }
            branch.consume();
            join(branch);
            return new CallNode(
                    Location.atIndex(source, openParen.start()),
                    location,
                    arguments.toArray(new AstNode[0])
            );
        }
        catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        try { return pullLiteral(); }
        catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        if (peek().tag() == Tag.OpenParenthesis) {
            try {
                Parser branch = branch();
                branch.consume();
                final AstNode inner = branch.pullExpression();
                final Token closing = branch.consume();
                if (closing.tag() != Tag.CloseParenthesis) {
                    throw new ParserError(
                            Location.atIndex(source, closing.start()),
                            "Expected closing parenthesis `)`"
                    );
                }
                join(branch);
                return inner;
            } catch (ParserError e) {
                accumulator = ParserError.accumulate(accumulator, e);
            }
        }

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

    public AstNode pullExpression3() throws ParserError {
        Parser branch = branch();
        AstNode accumulate = branch.pullExpression4();
        if (branch.peek().tag() == Tag.Star || branch.peek().tag() == Tag.Slash) {
            Token token = branch.consume();
            AstNode rhs = pullExpression3();
            accumulate = new BinaryOperatorNode(
                    Location.atIndex(source, token.start()),
                    token.tag(),
                    accumulate,
                    rhs
            );
        }
        join(branch);
        return accumulate;
    }

    public AstNode pullExpression2() throws ParserError {
        Parser branch = branch();
        AstNode accumulate = branch.pullExpression3();
        if (branch.peek().tag() == Tag.Plus || branch.peek().tag() == Tag.Minus) {
            Token token = branch.consume();
            AstNode rhs = pullExpression2();
            accumulate = new BinaryOperatorNode(
                    Location.atIndex(source, token.start()),
                    token.tag(),
                    accumulate,
                    rhs
            );
        }
        join(branch);
        return accumulate;
    }

    public AstNode pullExpression() throws ParserError {
        final Tag[] Operators = {
                Tag.Equal, Tag.NotEqual,
                Tag.GreaterEqual, Tag.Greater,
                Tag.LesserEqual, Tag.Less,
        };
        Parser branch = branch();
        AstNode accumulate = branch.pullExpression2();
        if (Arrays.asList(Operators).contains(branch.peek().tag())) {
            Token token = branch.consume();
            AstNode rhs = pullExpression2();
            accumulate = new BinaryOperatorNode(
                    Location.atIndex(source, token.start()),
                    token.tag(),
                    accumulate,
                    rhs
            );
        }
        join(branch);
        return accumulate;
    }

    // Statements and Definitions
    // Statements should not consume the trailing `;`.
    // This will be handled via `pullStatement` and `pullDeclaration`

    public AstNode pullVariableDeclaration() throws ParserError {
        Parser branch = branch();
        if (branch.peek().tag() != Tag.VarKeyword) {
            throw new ParserError(
                    Location.atIndex(source, branch.peek().start()),
                    "Expected `var` keyword"
            );
        }
        final int start = branch.consume().start();
        final Token identifier = branch.consume();
        if (identifier.tag() != Tag.Identifier) {
            throw new ParserError(
                    Location.atIndex(source, identifier.start()),
                    "Expected an identifier"
            );
        }
        if (branch.peek().tag() != Tag.Assign) {
            join(branch);
            return new VariableDeclarationNode(
                    Location.atIndex(source, start),
                    source.slice(identifier.start(), identifier.end())
            );
        }
        consume();
        final AstNode value = branch.pullExpression();
        join(branch);
        return new VariableDeclarationAndAssignmentNode(
                Location.atIndex(source, start),
                source.slice(identifier.start(), identifier.end()),
                value
        );
    }

    public AstNode pullAssignment() throws ParserError {
        Parser branch = branch();
        final AstNode location = branch.pullLocation();
        if (branch.peek().tag() != Tag.Assign) {
            throw new ParserError(
                    Location.atIndex(source, branch.peek().start()),
                    "Expected `=`"
            );
        }
        final Token focus = branch.consume();
        final AstNode value = branch.pullExpression();
        join(branch);
        return new AssignmentNode(
                Location.atIndex(source, focus.start()),
                location,
                value
        );
    }

    public AstNode pullImport() throws ParserError {
        Parser branch = branch();
        final Token start = branch.consume();
        if (start.tag() != Tag.ImportKeyword) {
            throw new ParserError(
                    Location.atIndex(source, start.start()),
                    "Expected `Import`"
            );
        }
        final Token source = consume();
        if (source.tag() != Tag.String) {
            throw new ParserError(
                    Location.atIndex(this.source, source.start()),
                    "Expected string literal"
            );
        }
        final Token identifier = consume();
        if (identifier.tag() != Tag.String) {
            throw new ParserError(
                    Location.atIndex(this.source, identifier.start()),
                    "Expected Identifier"
            );
        }
        join(branch);
        return new ImportNode(
                Location.atIndex(this.source, start.start()),
                this.source.slice(source.start(),source.end()),
                this.source.slice(identifier.start(),identifier.end())
        );
    }

    public  AstNode pullIf() throws ParserError {
        Parser branch = branch();
        final Token start = branch.consume();
        if (start.tag() != Tag.IfKeyword) {
            throw new ParserError(
                    Location.atIndex(source, start.start()),
                    "Expected `if` keyword"
            );
        }
        final AstNode condition = branch.pullExpression();
        final AstNode body = branch.pullBlock();
        if (branch.peek().tag() != Tag.ElseKeyword) {
            join(branch);
            return new IfNode(
                    Location.atIndex(source, start.start()),
                    condition,
                    body
            );
        }
        branch.consume();
        final AstNode elseCase = branch.pullBlock();
        join(branch);
        return new IfElseNode(
                Location.atIndex(source, start.start()),
                condition,
                body,
                elseCase
        );
    }

    public AstNode pullFunctionDefinition() throws ParserError {
        Parser branch = branch();
        final Token head = branch.consume();
        if (head.tag() != Tag.FnKeyword) {
            throw new ParserError(
                    Location.atIndex(source, head.start()),
                    "Expected `fn`"
            );
        }
        branch.consume();
        final CharBuffer name = branch.pullIdentifier().identifier;
        if (branch.peek().tag() != Tag.OpenParenthesis) {
            throw new ParserError(
                    Location.atIndex(source, branch.peek().start()),
                    "Expected `(`"
            );
        }
        ArrayList<CharBuffer> arguments = new ArrayList<CharBuffer>();
        do {
            if (branch.peek().tag() == Tag.CloseParenthesis) {
                break;
            }
            arguments.add(branch.pullIdentifier().identifier);
        } while (shouldContinueFuncArgs());
        if (branch.peek().tag() != Tag.CloseParenthesis) {
            throw new ParserError(
                    Location.atIndex(source, branch.peek().start()),
                    "Expected `)` after function arguments"
            );
        }
        branch.consume();
        final AstNode body = branch.pullBlock();
        join(branch);
        return new FunctionNode(
                Location.atIndex(source, head.start()),
                name,
                arguments.toArray(new CharBuffer[0]),
                body
        );
    }

    public AstNode pullWhile() throws ParserError {
        Parser branch = branch();
        final Token head = branch.consume();
        if (head.tag() != Tag.WhileKeyword) {
            throw new ParserError(
                    Location.atIndex(source, head.start()),
                    "Expected `while`"
            );
        }
        final AstNode condition = branch.pullExpression();
        final AstNode body = branch.pullBlock();

        join(branch);
        return new WhileNode(
                Location.atIndex(source, head.start()),
                condition,
                body
        );
    }

    public AstNode pullBlock() throws ParserError {
        Parser branch = branch();
        ArrayList<AstNode> statements = new ArrayList<AstNode>();
        final Token start = branch.consume();
        if (start.tag() != Tag.OpenBrace) {
            throw new ParserError(
                    Location.atIndex(source, start.start()),
                    "Expected {"
            );
        }
        while (branch.peek().tag() != Tag.CloseBrace) {
            statements.add(branch.pullStatement());
        }
        branch.consume();
        join(branch);
        return new BlockNode(
                Location.atIndex(source, start.start()),
                statements.toArray(new AstNode[0])
        );
    }
    public AstNode pullDeclaration() throws ParserError {
        Parser branch = branch();
        AstNode declaration = null;
        ParserError accumulator = null;

        try {
            declaration = branch.pullVariableDeclaration();
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        try {
            if (declaration != null) {
                declaration = branch.pullImport();
            }
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        try {
            if (declaration != null) {
                declaration = branch.pullFunctionDefinition();
            }
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }

        if (branch.peek().tag() != Tag.Semicolon) {
            throw new ParserError(
                    Location.atIndex(source,branch.consume().start()),
                    "Expected `;`"
            );
        }
        if (accumulator != null) {
            throw accumulator;
        }
        branch.consume();
        join(branch);
        return declaration;
    }

    public AstNode pullStatement() throws ParserError {
        ParserError accumulator = null;
        try {
            return pullDeclaration();
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }
        Parser branch = branch();
        // TODO: While
        AstNode statement = null;
        try {
            statement = pullAssignment();
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }
        try {
            if (statement != null) {
                statement = branch.pullIf();
            }
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }
        try {
            if (statement != null) {
                statement = branch.pullWhile();
            }
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }
        try {
            if (statement != null) {
                statement = branch.pullExpression();
            }
        } catch (ParserError e) {
            accumulator = ParserError.accumulate(accumulator, e);
        }
        if (branch.peek().tag() != Tag.Semicolon) {
            throw new ParserError(
                    Location.atIndex(source,branch.consume().start()),
                    "Expected `;`"
            );
        }
        if (accumulator != null) {
            throw accumulator;
        }
        branch.consume();
        join(branch);
        return statement;
    }
}
