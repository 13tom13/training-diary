package utils.mappers;

import entity.dto.UserDTO;
import entity.model.Rights;
import entity.model.Roles;
import entity.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "roles", source = "roles", qualifiedByName = "listToRoles"),
            @Mapping(target = "isActive", source = "active"),
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "isActive", source = "active"),
            @Mapping(target = "password", ignore = true)
    })
    User userDTOToUser(UserDTO userDTO);

    @Named("listToRights")
    default List<Rights> listToRights(List<Rights> rights) {
        if (rights != null) {
            return new ArrayList<>(rights);
        } else {
            return Collections.emptyList();
        }

    }

    @Named("listToRoles")
    default List<Roles> listToRoles(List<Roles> roles) {
        if (roles != null) {
            return new ArrayList<>(roles);
        } else {
            return Collections.emptyList();
        }

    }

}
