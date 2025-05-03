package syntactic.syntax;

import lexical.Location;

public class AssignmentNode extends AstNode {
    AstNode target;
    AstNode value;

    public AssignmentNode(Location location, AstNode target, AstNode value) {
        super(location);
        this.target = target;
        this.value = value;
    }

    @Override
    public <T> T visit(Visitor<T> visitor) {
        return visitor.visitAssignment(this);
    }
}
