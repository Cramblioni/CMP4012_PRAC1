package syntactic.syntax;

public interface Visitor<T> {
    T visitIdentifier(IdentifierNode node);
    T visitNumber(NumberNode node);
    T visitAccess(AccessNode node);
    T visitBinaryOperator(BinaryOperatorNode node);
    T visitMonadicOperator(MonadicOperatorNode node);
    T visitString(StringNode node);
}
