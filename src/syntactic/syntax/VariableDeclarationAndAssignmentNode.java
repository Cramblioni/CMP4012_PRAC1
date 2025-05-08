package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

public class VariableDeclarationAndAssignmentNode extends AstNode {
    public CharBuffer name;
    public AstNode value;

    public VariableDeclarationAndAssignmentNode(Location location, CharBuffer name, AstNode value) {
        super(location);
        this.name = name;
        this.value = value;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitVariableDeclarationAndAssignment(this);
    }
}
