package entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rights {

    private long id;

    private String name;

    private List<User> users;

    @Override
    public String toString() {
        return name;
    }
}
