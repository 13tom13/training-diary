package out.messengers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static utils.HTTP.sendPostRequest;

@Component
@RequiredArgsConstructor
public class AuthorizationMessenger {

    private String rootURL = "http://localhost:8080/training-diary";

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    public UserDTO login(AuthorizationDTO authorizationDTO) {
        String requestURL = rootURL + "/login";

        // Создаем заголовки запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем тело запроса
        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(authorizationDTO);
        } catch (JsonProcessingException e) {
            // обработка ошибки
            return null;
        }

        // Создаем HTTP сущность для передачи данных
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);

        // Выполняем POST-запрос с использованием RestTemplate
        ResponseEntity<UserDTO> responseEntity = restTemplate.exchange(requestURL, HttpMethod.POST, requestEntity, UserDTO.class);

        // Возвращаем результат запроса
        return responseEntity.getBody();
    }
}
