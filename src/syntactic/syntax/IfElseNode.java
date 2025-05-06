package syntactic.syntax;

import lexical.Location;

public class IfElseNode extends AstNode {
    AstNode condition;
    AstNode body;
    AstNode altBody;

    public IfElseNode(Location location, AstNode condition, AstNode body, AstNode altBody) {
        super(location);
        this.condition = condition;
        this.body = body;
        this.altBody = altBody;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitIfElse(this);
    }
}
