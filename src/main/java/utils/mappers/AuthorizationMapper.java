package utils.mappers;

import entity.dto.AuthorizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AuthorizationMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    AuthorizationDTO emailAndPasswordToAuthorizationDTO(String email, String password);
}
