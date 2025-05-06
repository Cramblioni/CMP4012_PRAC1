package syntactic.syntax;

import lexical.Location;

public class BooleanNode extends AstNode {
    public boolean value;

    public BooleanNode(Location location, boolean value) {
        super(location);
        this.value = value;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitBoolean(this);
    }
}
