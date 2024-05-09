package entity.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "permissions.roles")
public class Roles {
    @Id
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

