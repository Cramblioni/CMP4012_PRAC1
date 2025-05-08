package evaluation;

import syntactic.syntax.BooleanNode;
import syntactic.syntax.NumberNode;

public class ExpressionVisitor extends  BaseVisitor<BaseValue, DataError> {
    @Override
    public BaseValue visitNumber(NumberNode node) throws DataError {
        return new NumberValue(node.number);
    }
    @Override
    public BaseValue visitBoolean(BooleanNode node) throws DataError {
        return new BooleanValue(node.value);
    }
}
