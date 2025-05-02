package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

public class AccessNode extends AstNode {
    public AstNode source;
    public CharBuffer field;

    public AccessNode(Location location, AstNode source, CharBuffer field) {
        super(location);
        this.source = source;
        this.field = field;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitAccess(this);
    }
}
