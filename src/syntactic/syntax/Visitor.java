package syntactic.syntax;

public interface Visitor<T, E extends Throwable> {
    T visitIdentifier(IdentifierNode node) throws E;
    T visitNumber(NumberNode node) throws E;
    T visitAccess(AccessNode node) throws E;
    T visitBinaryOperator(BinaryOperatorNode node) throws E;
    T visitMonadicOperator(MonadicOperatorNode node) throws E;
    T visitString(StringNode node) throws E;
    T visitFunction(FunctionNode node) throws E;
    T visitVariableDeclaration(VariableDeclarationNode node) throws E;
    T visitVariableDeclarationAndAssignment(VariableDeclarationAndAssignmentNode node) throws E;
    T visitImport(ImportNode node) throws E;
    T visitIf(IfNode node) throws E;
    T visitIfElse(IfElseNode node) throws E;
    T visitWhile(WhileNode node) throws E;
    T visitBlock(BlockNode node) throws E;
    T visitAssignment(AssignmentNode node) throws E;
    T visitBoolean(BooleanNode node) throws E;
    T visitCall(CallNode node) throws E;
    T visitReturn(ReturnNode node) throws E;
}
