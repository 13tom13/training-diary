package in.exception;

public class ValidationException extends Exception {

    public ValidationException(String field) {
        super("field \"" + field + "\" cannot be empty");
    }
}
