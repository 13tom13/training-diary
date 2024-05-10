package exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String email) {
        super("Пользователь с email: " + email + " не найден");
    }

}
