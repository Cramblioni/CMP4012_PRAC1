package syntactic.syntax;

import lexical.Location;

public class IfNode extends AstNode {
    AstNode condition;
    AstNode body;

    public IfNode(Location location, AstNode condition, AstNode body) {
        super(location);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitIf(this);
    }
}
