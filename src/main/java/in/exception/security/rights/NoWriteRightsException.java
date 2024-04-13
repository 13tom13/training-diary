package in.exception.security.rights;

public class NoWriteRightsException extends NoRightsException{
    public NoWriteRightsException() {
        super("для записи");
    }
}
