package entities.dto;
/**
 * DTO для передачи данных о ролях пользователя.
 */
public class RolesDTO {

    private Long id; // Идентификатор роли
    private String name; // Название роли

    // Конструкторы, геттеры и сеттеры

    public RolesDTO() {
    }

    public RolesDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
