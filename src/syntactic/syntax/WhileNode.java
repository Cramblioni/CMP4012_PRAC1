package syntactic.syntax;

import lexical.Location;

public class WhileNode extends AstNode {
    AstNode condition;
    AstNode body;

    public  WhileNode(Location location, AstNode condition, AstNode body) {
        super(location);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitWhile(this);
    }
}
