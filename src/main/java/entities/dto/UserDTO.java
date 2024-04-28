package entities.dto;

import java.util.List;

/**
 * DTO для передачи данных о пользователе.
 */
public class UserDTO {

    private long id; // Идентификатор пользователя
    private String firstName; // Имя пользователя
    private String lastName; // Фамилия пользователя
    private String email; // Электронная почта пользователя
    private List<Long> rightsIds; // Список идентификаторов прав пользователя
    private List<Long> rolesIds; // Список идентификаторов ролей пользователя
    private boolean isActive; // Активность пользователя

    // Конструкторы, геттеры и сеттеры

    public UserDTO() {
    }

    public UserDTO(long id, String firstName, String lastName, String email, List<Long> rightsIds, List<Long> rolesIds, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rightsIds = rightsIds;
        this.rolesIds = rolesIds;
        this.isActive = isActive;
    }

    public UserDTO(String regFirstName, String regLastName, String regEmail, String regPassword) {
        this.firstName = regFirstName;
        this.lastName = regLastName;
        this.email = regEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getRightsIds() {
        return rightsIds;
    }

    public void setRightsIds(List<Long> rightsIds) {
        this.rightsIds = rightsIds;
    }

    public List<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        String status = isActive ? "active" : "deactivated";
        return "UserDTO{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", rightsIds=" + rightsIds +
               ", rolesIds=" + rolesIds +
               ", isActive=" + status +
               '}';
    }
}
