package in.controller.users;

/**
 * Интерфейс контроллера пользователей.
 */
public interface UserController {

    /**
     * Создает нового пользователя с заданными данными.
     *
     * @param firstName имя нового пользователя
     * @param lastName  фамилия нового пользователя
     * @param email     адрес электронной почты нового пользователя
     * @param password  пароль нового пользователя
     */
    void createNewUser(String firstName, String lastName, String email, String password);
}
