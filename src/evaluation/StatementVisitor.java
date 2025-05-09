package evaluation;

import syntactic.syntax.*;

public class StatementVisitor extends BaseVisitor<BaseValue, DataError> {
    Context module;
    Context local;

    public StatementVisitor(Context module, Context local) {
        this.module = module;
        this.local = local;
    }

    @Override
    public BaseValue visitExpression(ExpressionNode node) throws DataError {
        ExpressionVisitor sub = new ExpressionVisitor(module, local);
        node.expression.visit(sub);
        return null;
    }

    @Override
    public BaseValue visitVariableDeclaration(VariableDeclarationNode node) throws DataError {
        local.put(node.name.toString(), null);
        return null;
    }

    @Override
    public BaseValue visitVariableDeclarationAndAssignment(VariableDeclarationAndAssignmentNode node) throws DataError {
        ExpressionVisitor sub = new ExpressionVisitor(module, local);
        local.put(node.name.toString(), node.value.visit(sub));
        return null;
    }

    @Override
    public BaseValue visitAssignment(AssignmentNode node) throws DataError {
        ExpressionVisitor sub = new ExpressionVisitor(module, local);
        assert node.target instanceof IdentifierNode;
        local.put(((IdentifierNode)node.target).identifier.toString(), node.value.visit(sub));
        return null;
    }

    @Override
    public BaseValue visitIf(IfNode node) throws DataError {
        ExpressionVisitor sub = new ExpressionVisitor(module, local);
        BaseValue cond = node.condition.visit(sub);
        BodyVisitor limiter = new BodyVisitor(module, new Context(local));
        throw new DataError();
    }
}
