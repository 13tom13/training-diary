package out.messengers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static utils.HTTP.sendPostRequest;

@Component
@RequiredArgsConstructor
public class AuthorizationMessenger {

    @Value("${application.url}")
    private String rootURL;

    private final ObjectMapper objectMapper;

    public UserDTO login(AuthorizationDTO authorizationDTO) throws IOException {
        String loginServletPath = "/login";
        String requestURL = rootURL + loginServletPath;
        String jsonRequestBody = objectMapper.writeValueAsString(authorizationDTO);
        String jsonResponse = sendPostRequest(requestURL, jsonRequestBody);
        // Преобразование JSON ответа в объект UserDTO
        return objectMapper.readValue(jsonResponse, UserDTO.class);
    }
}
