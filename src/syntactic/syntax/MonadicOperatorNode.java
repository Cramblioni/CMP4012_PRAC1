package syntactic.syntax;

import lexical.Location;
import lexical.Tag;

public class MonadicOperatorNode extends AstNode {
    public Tag operator;
    public AstNode rhs;

    public MonadicOperatorNode(Location location, Tag operator, AstNode rhs) {
        super(location);
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitMonadicOperator(this);
    }
}
