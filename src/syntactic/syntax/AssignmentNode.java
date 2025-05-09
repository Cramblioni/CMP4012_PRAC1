package syntactic.syntax;

import lexical.Location;

public class AssignmentNode extends AstNode {
    public AstNode target;
    public AstNode value;

    public AssignmentNode(Location location, AstNode target, AstNode value) {
        super(location);
        this.target = target;
        this.value = value;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitAssignment(this);
    }
}
