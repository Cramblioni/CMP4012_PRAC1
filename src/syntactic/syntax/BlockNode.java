package syntactic.syntax;

import lexical.Location;

public class BlockNode extends AstNode {
    AstNode[] statements;

    public BlockNode(Location location, AstNode[] statements) {
        super(location);
        this.statements = statements;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitBlock(this);
    }
}
