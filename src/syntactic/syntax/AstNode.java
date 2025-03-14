package syntactic.syntax;

import lexical.Location;

public abstract class AstNode {
    Location location;

    public AstNode(Location location) {
        this.location = location;
    }

    public abstract <T> T visit(Visitor<T> visitor);
}

