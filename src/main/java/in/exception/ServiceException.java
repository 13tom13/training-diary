package in.exception;

public class ServiceException extends Exception {

    public ServiceException(String message) {
        super("Service exception in class:" + message);
    }
}
