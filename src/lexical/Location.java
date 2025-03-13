package lexical;

import java.nio.CharBuffer;

public record Location(int line, int column) {

    public final Location atInd(CharBuffer source, int ind) {
        int line = 1;
        int column = 1;
        for (int i = 0; i < ind; ++i) {
            if (source.get(i) == '\n') {
                line += 1;
                column = 1;
            }
            column += 1;
        }
        return new Location(line, column);
    }
}
