package adg.backend.model.exceptions;

public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException(Long id)
    {
        super("Ad with id " + id + " not found.");
    }
}
