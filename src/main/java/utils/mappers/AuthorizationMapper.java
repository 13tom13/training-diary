package utils.mappers;

import entity.dto.AuthorizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface AuthorizationMapper {

    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    AuthorizationDTO emailAndPasswordToAuthorizationDTO(String email, String password);
}
