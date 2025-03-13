package syntactic.syntax;

public abstract class AstNode {

    public abstract <T> T visit(Visitor<T> visitor);
}

