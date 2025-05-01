package syntactic;

import lexical.Location;

public class ParserError extends Exception {
    Location pos;
    public  ParserError(Location pos, String message) {
        super(message);
        this.pos = pos;
    }

    public ParserError merge(ParserError other) {
        if (other.pos.further(this.pos)) {
            return other;
        }
        return this;
    }
}
