package syntactic.syntax;

public interface Visitor<T> {
    T visitIdentifier(IdentifierNode node);
    T visitNumber(NumberNode node);
    T visitAccess(AccessNode node);
    T visitOperator(OperatorNode node);
}
