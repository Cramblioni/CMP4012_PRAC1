package evaluation;

import syntactic.syntax.BooleanNode;
import syntactic.syntax.CallNode;
import syntactic.syntax.NumberNode;
import syntactic.syntax.StringNode;

public class ExpressionVisitor extends  BaseVisitor<BaseValue, DataError> {
    Context module;
    Context local;

    public ExpressionVisitor(Context module, Context local) {
        this.module = module;
        this.local = local;
    }
    @Override
    public BaseValue visitNumber(NumberNode node) throws DataError {
        return new NumberValue(node.number);
    }
    @Override
    public BaseValue visitBoolean(BooleanNode node) throws DataError {
        return new BooleanValue(node.value);
    }
    @Override
    public BaseValue visitString(StringNode node) throws DataError {
        return new StringValue(node.contents);
    }

    @Override
    public BaseValue visitCall(CallNode node) throws  DataError {
        BaseValue[] args = new BaseValue[node.args.length];
        for (int i = 0; i < args.length; i+=1) {
            args[i] = node.args[i].visit(this);
        }
        final BaseValue funcdat = node.func.visit(this);
        if (funcdat instanceof FunctionValue func) {
            Context sub = new Context(module);
            for (int i = 0; i < args.length; i+=1) {
                sub.put(
                        func.decl.argumentNames[i].toString(),
                        args[i]
                );
            }
            ExpressionVisitor forfunc = new BodyVisitor(module, sub);
            func.decl.body.visit(forfunc);
        }
        if (funcdat instanceof JavaFuncWrapper func) {
            return func.doCall(args);
        }
    }
}
