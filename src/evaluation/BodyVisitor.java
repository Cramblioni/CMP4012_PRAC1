package evaluation;

import syntactic.syntax.AstNode;
import syntactic.syntax.BlockNode;

public class BodyVisitor extends StatementVisitor {

    public BodyVisitor(Context module, Context local) {
        super(module, local);
    }

    @Override
    public BaseValue visitBlock(BlockNode node) throws DataError {
        for (AstNode subs : node.statements) {
            subs.visit(this);
        }
        return null;
    }
}
