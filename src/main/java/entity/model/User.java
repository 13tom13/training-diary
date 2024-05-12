package entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий пользователя.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id; // Идентификатор пользователя
    private String firstName; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private String email; // Электронная почта пользователя
    private String password; // Пароль пользователя
    private List<Rights> rights = new ArrayList<>(); // Права пользователя
    private List<Roles> roles = new ArrayList<>(); // Роли пользователя
    private boolean isActive; // Активность пользователя

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
