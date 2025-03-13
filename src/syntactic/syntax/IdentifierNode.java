package syntactic.syntax;

public class IdentifierNode extends AstNode {
    String identifier;

    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }
}
