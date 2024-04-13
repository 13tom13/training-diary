package in.exception.security.rights;

public class NoDeleteRightsException extends NoRightsException{
    public NoDeleteRightsException() {
        super("удаления");
    }
}
