package syntactic.syntax;

import lexical.Location;

public class StringNode extends AstNode {
    String contents;
    public StringNode(Location location, String contents) {
        super(location);
        this.contents = contents;
    }
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitString(this);
    }
}
