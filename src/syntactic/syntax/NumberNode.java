package syntactic.syntax;

import lexical.Location;

public class NumberNode extends AstNode {
    public Float number;
    public NumberNode(Location location, float value) {
        super(location);
        number = value;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitNumber(this);
    }
}
