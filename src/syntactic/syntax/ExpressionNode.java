package syntactic.syntax;


import lexical.Location;

public class ExpressionNode extends AstNode {
    public AstNode expression;

    public ExpressionNode(Location loc, AstNode expr) {
        super(loc);
        expression = expr;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitExpression(this);
    }
}
