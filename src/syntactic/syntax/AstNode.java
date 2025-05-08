package syntactic.syntax;

import lexical.Location;

public abstract class AstNode {
    public Location location;

    public AstNode(Location location) {
        this.location = location;
    }

    public abstract <T, E extends Throwable> T visit(Visitor<T, E> visitor) throws E;
}

