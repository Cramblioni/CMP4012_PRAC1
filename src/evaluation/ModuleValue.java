package evaluation;

import syntactic.syntax.AstNode;
import syntactic.syntax.FunctionNode;
import syntactic.syntax.VariableDeclarationNode;

import java.util.Hashtable;

public class ModuleValue extends BaseValue {
    Hashtable<String, AstNode> fields;

    public ModuleValue(AstNode[] toplevel, Context context) {
        fields = new Hashtable<>();
        for (AstNode decl : toplevel) {
            if (decl instanceof FunctionNode func) {
                fields.put(func.name.toString(), decl);
                continue;
            }
            if (decl instanceof VariableDeclarationNode decl)
        }
    }
}
