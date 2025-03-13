package syntactic.syntax;

public class NumberNode extends AstNode {
    Float number;
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitNumber(this);
    }
}
