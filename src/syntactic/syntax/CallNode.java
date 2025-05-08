package syntactic.syntax;

import lexical.Location;

public class CallNode extends AstNode {
    public AstNode func;
    public AstNode[] args;

    public CallNode(Location location, AstNode func, AstNode[] args) {
        super(location);
        this.func = func;
        this.args = args;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitCall(this);
    }
}
