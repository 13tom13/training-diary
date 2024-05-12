package out.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.RegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserHTTPMessenger {

    @Value("${application.url}")
    private String rootURL;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

//    public void registration(RegistrationDTO registrationDTO) throws IOException {
//        String controllertUrl = rootURL + "/registration";
//        String jsonRequestBody = objectMapper.writeValueAsString(registrationDTO);
//        String response = sendPostRequest(controllertUrl, jsonRequestBody);
//        System.out.println("Ответ от сервера: " + response);
//    }
    public void registration(RegistrationDTO registrationDTO) throws IOException {
        String controllerUrl = rootURL + "/registration";
        String jsonRequestBody = objectMapper.writeValueAsString(registrationDTO);
        String response = restTemplate.postForObject(controllerUrl, jsonRequestBody, String.class);
        System.out.println("Ответ от сервера: " + response);
    }

}