package syntactic.syntax;

import lexical.Location;

public class BlockNode extends AstNode {
    AstNode[] statements;

    public BlockNode(Location location, AstNode[] statements) {
        super(location);
        this.statements = statements;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitBlock(this);
    }
}
