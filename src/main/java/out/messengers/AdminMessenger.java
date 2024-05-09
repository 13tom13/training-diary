package out.messengers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.UserDTO;
import entity.model.Rights;
import entity.model.User;
import exceptions.RepositoryException;
import exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.ServletUtils.encodeToUrlJson;
import static utils.HTTP.*;

@Component
@RequiredArgsConstructor
public class AdminMessenger {

    @Value("${application.url}")
    private String rootURL;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;


    public  List<User> getAllUsers() {
        String servletUrl = rootURL + "/admin/users";
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


    public UserDTO getUser (String email) throws UserNotFoundException, JsonProcessingException {
        String userEmailForGet = encodeToUrlJson(email);
        String urlWithParams = rootURL + "/admin/user" + "?userEmail=" + userEmailForGet;
        try {
            String jsonResponse = sendGetRequest(urlWithParams);
            return objectMapper.readValue(jsonResponse, UserDTO.class);
        } catch (IOException e) {
            throw new UserNotFoundException(email);
        }
    }

    public void deleteUser(UserDTO userDTO) {
        try {
            String userEmailForGet = encodeToUrlJson(userDTO.getEmail());
            String urlWithParams = rootURL + "/admin/user/delete" + "?userEmail=" + userEmailForGet;
            try {
                sendDeleteRequest(urlWithParams);
            } catch (IOException e) {
                System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при кодировании данных: " + e.getMessage());
        }
    }

    public UserDTO changeUserName(UserDTO userDTO, String newName) throws RepositoryException {
        String requestURL = rootURL + "/admin/user/change/username";
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

//    public ResponseEntity<UserDTO> changeUserLastName(UserDTO userDTO, String newLastName) throws RepositoryException {
//        try {
//            String changeUserLastNameServletPath = "/admin/user/change/lastname";
//            String requestURL = rootURL + changeUserLastNameServletPath;
//            Map<String, Object> combinedMap = new HashMap<>();
//            combinedMap.put("userDTO", userDTO);
//            combinedMap.put("newLastName", newLastName);
//            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
//            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
//            return objectMapper.readValue(changeUser, UserDTO.class);
//        } catch (IOException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<UserDTO> changeUserPassword(UserDTO userDTO, String newPassword) throws RepositoryException {
//        String changeUserPasswordServletPath = "/admin/user/change/password";
//        String requestURL = rootURL + changeUserPasswordServletPath;
//        try {
//            Map<String, Object> combinedMap = new HashMap<>();
//            combinedMap.put("userDTO", userDTO);
//            combinedMap.put("newPassword", newPassword);
//            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
//            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
//            return objectMapper.readValue(changeUser, UserDTO.class);
//        } catch (IOException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<UserDTO> changeUserActive(UserDTO userDTO) throws RepositoryException {
//        String changeUserActiveServletPath = "/admin/user/change/active";
//        String requestURL = rootURL + changeUserActiveServletPath;
//        try {
//            Map<String, Object> combinedMap = new HashMap<>();
//            combinedMap.put("userDTO", userDTO);
//            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
//            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
//            return objectMapper.readValue(changeUser, UserDTO.class);
//        } catch (IOException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<UserDTO> changeUserRights(UserDTO userDTO, List<Rights> userRights) throws RepositoryException {
//        String changeUserRightsServletPath = "/admin/user/change/rights";
//        String requestURL = rootURL + changeUserRightsServletPath;
//        try {
//            Map<String, Object> combinedMap = new HashMap<>();
//            combinedMap.put("userDTO", userDTO);
//            combinedMap.put("userRights", userRights);
//            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
//            String changeUser = sendPutRequest(requestURL, jsonRequestBody);
//            return objectMapper.readValue(changeUser, UserDTO.class);
//        } catch (IOException e) {
//            throw new RepositoryException(e.getMessage());
//        }
//    }
//
//    @Override
//    public ResponseEntity<List<Rights>> getAllRights() throws IOException {
//        String getAllRightsServletPath = "/admin/rights";
//        String urlWithParams = rootURL + getAllRightsServletPath;
//        String jsonResponse = sendGetRequest(urlWithParams);
//        TypeReference<List<Rights>> typeRef = new TypeReference<>() {
//        };
//        return objectMapper.readValue(jsonResponse, typeRef);
//    }
}
