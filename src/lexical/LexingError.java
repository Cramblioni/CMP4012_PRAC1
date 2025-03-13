package lexical;

public class LexingError extends Exception {
    Location at;

    public LexingError(Location at, String message) {
        super(message);
        this.at = at;
    }
    public LexingError(Location at, String message, Throwable cause) {
        super(message, cause);
        this.at = at;
    }

    @Override
    public String getMessage() {
        return at.toString() + super.getMessage();
    }
}
