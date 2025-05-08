
package syntactic.syntax;

import lexical.Location;

import java.nio.CharBuffer;

// Java/Intellij does not like using optional values instead of nulls
public class VariableDeclarationNode extends AstNode {
    public CharBuffer name;

    public VariableDeclarationNode(Location location, CharBuffer name) {
        super(location);
        this.name = name;
    }

    @Override
    public <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E {
        return visitor.visitVariableDeclaration(this);
    }
}
