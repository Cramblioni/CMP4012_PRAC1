package evaluation;

import syntactic.syntax.*;

import java.util.Hashtable;

public class ModuleValue extends BaseValue {
    Context fields;

    public ModuleValue(AstNode[] toplevel, Context context) throws DataError {
        fields = new Context();
        for (AstNode decl : toplevel) {
            if (decl instanceof FunctionNode func) {
                fields.put(func.name.toString(), new FunctionValue(func));
                continue;
            }
            if (decl instanceof VariableDeclarationNode vdecl) {
                fields.put(vdecl.name.toString(), null);
            }
            if (decl instanceof VariableDeclarationAndAssignmentNode vdecl) {
                ExpressionVisitor sub = new ExpressionVisitor(fields, new Context(fields));
                fields.put(vdecl.name.toString(), vdecl.value.visit(sub));
            }
            if (decl instanceof ImportNode) {
                throw new DataError();
            }
        }
    }
}
