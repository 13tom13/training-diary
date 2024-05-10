package entity.model;

import java.util.List;

public class Rights {

    private Long id;

    private String name;

    private List<User> users;

    @Override
    public String toString() {
        return name;
    }

    public Rights(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Rights() {
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
}
