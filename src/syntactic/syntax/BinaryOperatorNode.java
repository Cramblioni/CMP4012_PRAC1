package syntactic.syntax;

import lexical.Location;
import lexical.Tag;

public class BinaryOperatorNode extends AstNode {
    public Tag operator;
    public AstNode lhs;
    public AstNode rhs;

    public BinaryOperatorNode(Location location, Tag operator, AstNode lhs, AstNode rhs) {
        super(location);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitBinaryOperator(this);
    }
}
