package evaluation;

import syntactic.syntax.FunctionNode;

public class FunctionValue extends BaseValue {
    public FunctionNode decl;

    public FunctionValue(FunctionNode decl) {
        this.decl = decl;
    }
}
