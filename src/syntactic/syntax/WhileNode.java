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
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitWhile(this);
    }
}
