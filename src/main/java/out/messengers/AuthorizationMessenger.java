package out.messengers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthorizationMessenger {

    private final String rootURL = "http://localhost:8080/training-diary";

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    public UserDTO login(AuthorizationDTO authorizationDTO) {
        String requestURL = rootURL + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(authorizationDTO);
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);
            ResponseEntity<UserDTO> responseEntity = restTemplate.exchange(requestURL, HttpMethod.POST, requestEntity, UserDTO.class);

            return responseEntity.getBody();
        } catch (JsonProcessingException e) {
            return null;
        }

    }
}
