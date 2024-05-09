package in.controller.users.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.UserDTO;
import entity.model.Rights;
import entity.model.User;
import exceptions.RepositoryException;
import exceptions.UserNotFoundException;
import in.controller.users.AdminController;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import utils.ValidatorFactoryProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static config.ApplicationConfig.getRootURL;
import static servlet.utils.ServletUtils.OBJECT_MAPPER;
import static servlet.utils.ServletUtils.encodeToUrlJson;
import static utils.HTTP.*;

public class AdminControllerHTTP implements AdminController {

    private final String rootURL = getRootURL();
    private final ObjectMapper objectMapper = OBJECT_MAPPER;
    private final String getAllUsersServletPath = "/admin/users";
    private final String getUserServletPath = "/admin/user";
    private final String deleteUserServletPath = "/admin/user/delete";
    private final String changeUserNameServletPath = "/admin/user/change/username";
    private final String changeUserLastNameServletPath = "/admin/user/change/lastname";
    private final String changeUserPasswordServletPath = "/admin/user/change/password";
    private final String changeUserActiveServletPath = "/admin/user/change/active";
    private final String changeUserRightsServletPath = "/admin/user/change/rights";
    private final String getAllRightsServletPath = "/admin/rights";

    private final Validator validator = ValidatorFactoryProvider.getValidator();

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        String servletUrl = rootURL + getAllUsersServletPath;
        try {
            String jsonResponse = sendGetRequest(servletUrl);
            try {
                TypeReference<List<User>> typeRef = new TypeReference<>() {
                };
                return objectMapper.readValue(jsonResponse, typeRef);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<UserDTO> getUser(@RequestParam String email) throws UserNotFoundException, JsonProcessingException {
        String userEmailForGet = encodeToUrlJson(email);
        String urlWithParams = rootURL + getUserServletPath + "?userEmail=" + userEmailForGet;
        try {
            String jsonResponse = sendGetRequest(urlWithParams);
            return objectMapper.readValue(jsonResponse, UserDTO.class);
        } catch (IOException e) {
            throw new UserNotFoundException(email);
        }
    }

    @Override
    public ResponseEntity<Void> deleteUser(UserDTO userDTO) {
        try {
            String userEmailForGet = encodeToUrlJson(userDTO.getEmail());
            String urlWithParams = rootURL + deleteUserServletPath + "?userEmail=" + userEmailForGet;
            try {
                sendDeleteRequest(urlWithParams);
            } catch (IOException e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при кодировании данных: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> changeUserName(UserDTO userDTO, String newName) throws RepositoryException {
        String requestURL = rootURL + changeUserNameServletPath;
        try {
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("newName", newName);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(changeUser, UserDTO.class);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<UserDTO> changeUserLastName(UserDTO userDTO, String newLastName) throws RepositoryException {
        try {
            String requestURL = rootURL + changeUserLastNameServletPath;
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("newLastName", newLastName);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(changeUser, UserDTO.class);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<UserDTO> changeUserPassword(UserDTO userDTO, String newPassword) throws RepositoryException {
        String requestURL = rootURL + changeUserPasswordServletPath;
        try {
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("newPassword", newPassword);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(changeUser, UserDTO.class);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<UserDTO> changeUserActive(UserDTO userDTO) throws RepositoryException {
        String requestURL = rootURL + changeUserActiveServletPath;
        try {
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(changeUser, UserDTO.class);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<UserDTO> changeUserRights(UserDTO userDTO, List<Rights> userRights) throws RepositoryException {
        String requestURL = rootURL + changeUserRightsServletPath;
        try {
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("userRights", userRights);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(changeUser, UserDTO.class);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Rights>> getAllRights() throws IOException {
        String urlWithParams = rootURL + getAllRightsServletPath;
        String jsonResponse = sendGetRequest(urlWithParams);
        TypeReference<List<Rights>> typeRef = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonResponse, typeRef);
    }
}
