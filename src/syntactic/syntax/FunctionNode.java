package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

public class FunctionNode extends AstNode {
    public CharBuffer name;
    public CharBuffer[] argumentNames;
    public AstNode body;

    public FunctionNode(Location pos, CharBuffer name, CharBuffer[] args, AstNode body) {
        super(pos);
        this.name = name;
        this.argumentNames = args;
        this.body = body;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitFunction(this);
    }
}
