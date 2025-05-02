package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

public class IdentifierNode extends AstNode {
    public CharBuffer identifier;

    public IdentifierNode(Location location, CharBuffer text) {
        super(location);
        this.identifier = text;
    }
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }
}
