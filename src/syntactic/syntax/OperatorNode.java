package syntactic.syntax;

import lexical.Location;
import lexical.Tag;

public class OperatorNode extends AstNode {
    Tag operator;
    AstNode lhs;
    AstNode rhs;

    public OperatorNode(Location location, Tag operator, AstNode lhs, AstNode rhs) {
        super(location);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitOperator(this);
    }
}
