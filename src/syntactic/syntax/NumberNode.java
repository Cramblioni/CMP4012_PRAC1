package syntactic.syntax;

import lexical.Location;

public class NumberNode extends AstNode {
    Float number;
    public NumberNode(Location location, float value) {
        super(location);
        number = value;
    }
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitNumber(this);
    }
}
