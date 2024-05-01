package entities.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий пользователя.
 */
public class User {

    private long id; // Идентификатор пользователя
    private String firstName; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private String email; // Электронная почта пользователя
    private String password; // Пароль пользователя
    private List<Rights> rights = new ArrayList<>(); // Права пользователя
    private List<Roles> roles = new ArrayList<>(); // Роли пользователя
    private boolean isActive = true; // Активность пользователя

    /**
     * Конструктор с параметрами.
     *
     * @param firstName Имя пользователя
     * @param lastName  Фамилия пользователя
     * @param email     Электронная почта пользователя
     * @param password  Пароль пользователя
     */
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(long id, String firstName, String lastName, String email, String password, List<Rights> rights, List<Roles> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rights = rights;
        this.roles = roles;
    }

    /**
     * Пустой конструктор.
     */
    public User() {
    }

    /**
     * Получает идентификатор пользователя.
     */
    public long getId() {
        return id;
    }

    /**
     *  Устанавливает идентификатор пользователя.
     * @param id идентификатор пользователя
     */
    public void setId(long id) {
        this.id = id;
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

    /**
     * Получает права пользователя.
     *
     * @return Список прав пользователя
     */
    public List<Rights> getRights() {
        return rights;
    }

    /**
     * Получает роли пользователя.
     *
     * @return Список ролей пользователя
     */
    public List<Roles> getRoles() {
        return roles;
    }


    /**
     * Устанавливает роли для пользователя.
     *
     * @param rolesForSet Список ролей для установки
     */
    public void setRoles(List<Roles> rolesForSet) {
        roles.addAll(rolesForSet);
    }

    /**
     * Устанавливает права пользователя.
     *
     * @param rightsForSet Список прав для установки
     */
    public void setRights(List<Rights> rightsForSet) {
        this.rights = rightsForSet;
    }

    /**
     * Проверяет, активен ли пользователь.
     *
     * @return {@code true}, если пользователь активен, в противном случае {@code false}
     */
    public boolean isActive() {
        return isActive;
    }


    /**
     * Устанавливает статус активности пользователя.
     *
     * @param active Новый статус активности пользователя
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Возвращает строковое представление пользователя.
     *
     * @return Строковое представление пользователя
     */
    @Override
    public String toString() {
        String status = isActive ? "active" : "deactivate";
        return "Пользователь: " + firstName + " " + lastName + " | email: " + email + " | роли: "
                + roles.toString() + " | права: " + rights.toString() + " | (" + status + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
