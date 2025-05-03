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
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitIf(this);
    }
}
