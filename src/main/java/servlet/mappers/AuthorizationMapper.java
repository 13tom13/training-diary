package servlet.mappers;

import entities.dto.AuthorizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public interface AuthorizationMapper {

    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    AuthorizationDTO emailAndPasswordToAuthorizationDTO(String email, String password);
}
