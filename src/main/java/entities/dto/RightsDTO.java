package entities.dto;
/**
 * DTO для передачи данных о правах пользователя.
 */
public class RightsDTO {

    private Long id; // Идентификатор права
    private String name; // Название права

    // Конструкторы, геттеры и сеттеры

    public RightsDTO() {
    }

    public RightsDTO(Long id, String name) {
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
