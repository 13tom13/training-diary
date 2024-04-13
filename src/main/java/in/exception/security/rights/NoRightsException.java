package in.exception.security.rights;

public abstract class NoRightsException extends Exception{

    public NoRightsException(String message) {
        super("У вас нет прав для " + message);
    }
}
