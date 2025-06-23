package adg.backend.model.exceptions;

public class InvalidAdException extends RuntimeException {
    public InvalidAdException() {
        super("Invalid ad data provided.");
    }
}
