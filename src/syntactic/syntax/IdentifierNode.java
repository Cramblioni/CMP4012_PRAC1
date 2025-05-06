package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

public class IdentifierNode extends AstNode {
    public CharBuffer identifier;

    public IdentifierNode(Location location, CharBuffer text) {
        super(location);
        this.identifier = text;
    }
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitIdentifier(this);
    }
}
