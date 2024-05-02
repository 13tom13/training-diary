package in.controller.users.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.RegistrationDTO;
import in.controller.users.UserController;

import java.io.IOException;

import static config.ApplicationConfig.getRootURL;
import static utils.HTTP.sendPostRequest;

public class UserControllerHTTP implements UserController {

    private final String rootURL = getRootURL();
    private final String registerServletPath = "/registration";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void createNewUser(RegistrationDTO registrationDTO) throws IOException {
        String servletUrl = rootURL + registerServletPath;
        String jsonRequestBody = objectMapper.writeValueAsString(registrationDTO);
        String response = sendPostRequest(servletUrl, jsonRequestBody);
        System.out.println("Ответ от сервера: " + response);
    }
}
