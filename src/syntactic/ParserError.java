package syntactic;

import lexical.Location;

public class ParserError extends Exception {
    Location pos;
    boolean eof = false;
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
    public boolean isEOF() {
        return this.eof;
    }
    public void markEOF() {
        this.eof = true;
    }

    public static ParserError accumulate(ParserError accumulator, ParserError next) {
        if (accumulator == null) return next;
        if (next == null) return accumulator;
        return accumulator.merge(next);
    }
}
