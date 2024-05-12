package out.messenger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.AuthorizationDTO;
import entity.dto.UserDTO;
import entity.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorizationHTTPMessenger {

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
            e.printStackTrace();
            return null;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return null;
            } else {
                // Если код состояния ответа не UNAUTHORIZED, бросаем исключение дальше
                throw e;
            }
        }
    }

    public List<Roles> getUserRoles(UserDTO userDTO) {
        String requestURL = rootURL + "/getUserRoles?userId=" + userDTO.getId();
        ResponseEntity<List<Roles>> responseEntity = restTemplate.exchange(requestURL,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Roles>>() {
                });
        return responseEntity.getBody();

    }
}
