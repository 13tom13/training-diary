package dto;

public class UserDTO {

    private String firstName; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private String email; // Электронная почта пользователя
    private String password; // Пароль пользователя

    /**
     * Конструктор с параметрами.
     *
     * @param firstName Имя пользователя
     * @param lastName  Фамилия пользователя
     * @param email     Электронная почта пользователя
     * @param password  Пароль пользователя
     */
    public UserDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Получает имя пользователя.
     *
     * @return Имя пользователя
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param firstName Новое имя пользователя
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Получает фамилию пользователя.
     *
     * @return Фамилия пользователя
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Устанавливает фамилию пользователя.
     *
     * @param lastName Новая фамилия пользователя
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Получает электронную почту пользователя.
     *
     * @return Электронная почта пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает электронную почту пользователя.
     *
     * @param email Новая электронная почта пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Получает пароль пользователя.
     *
     * @return Пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password Новый пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
