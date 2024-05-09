package in.controller.authorization.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import in.controller.authorization.AuthorizationController;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static config.ApplicationConfig.getRootURL;
import static utils.HTTP.sendPostRequest;

public class AuthorizationControllerHTTP implements AuthorizationController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String rootURL = getRootURL();
    private final String loginServletPath = "/login";

    @Override
    public ResponseEntity<?> login(AuthorizationDTO authorizationDTO) throws IOException {
        String requestURL = rootURL + loginServletPath;
        String jsonRequestBody = objectMapper.writeValueAsString(authorizationDTO);
        String jsonResponse = sendPostRequest(requestURL, jsonRequestBody);
        // Преобразование JSON ответа в объект UserDTO
        return objectMapper.readValue(jsonResponse, ResponseEntity.class);
//        } else {
//            throw new ConstraintViolationException(validate);
//        }

    }
}
