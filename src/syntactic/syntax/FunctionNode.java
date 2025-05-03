package syntactic.syntax;

import lexical.Location;

public class FunctionNode extends AstNode {
    public AstNode name;
    public AstNode[] argumentNames;
    public AstNode body;

    public FunctionNode(Location pos, AstNode name, AstNode[] args, AstNode body) {
        super(pos);
        this.name = name;
        this.argumentNames = args;
        this.body = body;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitFunction(this);
    }
}
