package exception;

public class ValidationException extends Exception {

    public ValidationException(String message, Class<?> callingClass) {
        super("Validation exception in class " + callingClass.getSimpleName() + ": " + message);
    }
}
