package in.exception.security.rights;

public class NoEditRightsException extends NoRightsException {
    public NoEditRightsException() {
        super("изменения");
    }
}
