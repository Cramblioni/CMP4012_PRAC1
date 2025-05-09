package syntactic.syntax;

import lexical.Location;

public class ReturnNode extends AstNode {
    public AstNode value;
    public ReturnNode(Location location, AstNode value) {
        super(location);
        this.value = value;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitReturn(this);
    }
}
