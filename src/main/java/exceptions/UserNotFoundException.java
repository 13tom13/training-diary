package exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }

}
