package evaluation;

import syntactic.syntax.*;

public class BaseVisitor<T, E extends Throwable> implements Visitor<T, E> {
    private E base_exception;
    public T visitIdentifier(IdentifierNode node) throws E { throw base_exception; }
    public T visitNumber(NumberNode node) throws E { throw base_exception; }
    public T visitAccess(AccessNode node) throws E { throw base_exception; }
    public T visitBinaryOperator(BinaryOperatorNode node) throws E { throw base_exception; }
    public T visitMonadicOperator(MonadicOperatorNode node) throws E { throw base_exception; }
    public T visitString(StringNode node) throws E { throw base_exception; }
    public T visitFunction(FunctionNode node) throws E { throw base_exception; }
    public T visitVariableDeclaration(VariableDeclarationNode node) throws E { throw base_exception; }
    public T visitVariableDeclarationAndAssignment(VariableDeclarationAndAssignmentNode node) throws E { throw base_exception; }
    public T visitImport(ImportNode node) throws E { throw base_exception; }
    public T visitIf(IfNode node) throws E { throw base_exception; }
    public T visitIfElse(IfElseNode node) throws E { throw base_exception; }
    public T visitWhile(WhileNode node) throws E { throw base_exception; }
    public T visitBlock(BlockNode node) throws E { throw base_exception; }
    public T visitAssignment(AssignmentNode node) throws E { throw base_exception; }
    public T visitBoolean(BooleanNode node) throws E { throw base_exception; }
    public T visitCall(CallNode node) throws E { throw base_exception; }
}
