package syntactic.syntax;

import lexical.Location;

public class StringNode extends AstNode {
    public String contents;
    public StringNode(Location location, String contents) {
        super(location);
        this.contents = contents;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitString(this);
    }
}
