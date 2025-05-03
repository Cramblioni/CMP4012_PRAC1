package syntactic.syntax;

public interface Visitor<T> {
    T visitIdentifier(IdentifierNode node);
    T visitNumber(NumberNode node);
    T visitAccess(AccessNode node);
    T visitBinaryOperator(BinaryOperatorNode node);
    T visitMonadicOperator(MonadicOperatorNode node);
    T visitString(StringNode node);
    T visitFunction(FunctionNode node);
    T visitVariableDeclaration(VariableDeclarationNode node);
    T visitVariableDeclarationAndAssignment(VariableDeclarationAndAssignmentNode node);
    T visitImport(ImportNode node);
    T visitIf(IfNode node);
    T visitIfElse(IfElseNode node);
    T visitWhile(WhileNode node);
    T visitBlock(BlockNode node);
    T visitAssignment(AssignmentNode node);
}
