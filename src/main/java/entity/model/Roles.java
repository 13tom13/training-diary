package entity.model;

import java.util.List;

public class Roles {

    private Long id;

    private String name;

    private List<User> users;

    public Roles(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Roles() {

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

