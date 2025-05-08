package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

public class ImportNode extends AstNode {
    public CharBuffer source;
    public CharBuffer identifier;

    public ImportNode(Location location, CharBuffer source, CharBuffer identifier) {
        super(location);
        this.source = source;
        this.identifier = identifier;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitImport(this);
    }
}
