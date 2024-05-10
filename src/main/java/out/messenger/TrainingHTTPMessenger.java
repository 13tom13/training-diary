package out.messenger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.HTTP.sendGetRequest;

@Component
@RequiredArgsConstructor
public class TrainingHTTPMessenger {

    private final String rootURL = "http://localhost:8080/training-diary";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public TreeMap<LocalDate, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) throws RestClientException, JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(userDTO);
        System.out.println(userDTO);

        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/AllTrainings")
                .queryParam("user", userJson) // Обратите внимание на эту строку
                .toUriString();
        try {
            String jsonResponse = sendGetRequest(url);
            TypeReference<TreeMap<LocalDate, TreeSet<TrainingDTO>>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(jsonResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, String trainingDate) throws RestClientException, JsonProcessingException {
        String url = rootURL + "/training/getTrainingByDate?user=" + userDTO + "&date=" + trainingDate;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();
        TypeReference<TreeSet<TrainingDTO>> typeRef = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonResponse, typeRef);
    }

    public TrainingDTO getTrainingByUserEmailAndDateAndName(UserDTO userDTO, String trainingDate, String trainingName) throws RestClientException, JsonProcessingException {
        String url = rootURL + "/training/getTraining?user=" + userDTO + "&date=" + trainingDate + "&name=" + trainingName;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public List<String> getTrainingTypes(UserDTO userDTO) throws RestClientException, JsonProcessingException {
        String url = rootURL + "/training/getTrainingTypes?user=" + userDTO;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();
        TypeReference<List<String>> typeRef = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonResponse, typeRef);
    }

    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/save";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, new Object[]{userDTO, trainingDTO}, String.class);
        String jsonResponse = responseEntity.getBody();
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public void deleteTraining(UserDTO userDTO, String date, String name) throws RestClientException, JsonProcessingException {
        String url = rootURL + "/training/delete?user=" + userDTO + "&date=" + date + "&name=" + name;
        restTemplate.delete(url);
    }

    public void saveTrainingType(UserDTO userDTO, String customTrainingType) throws RestClientException, JsonProcessingException {
        String url = rootURL + "/training/saveTrainingTypes";
        restTemplate.postForEntity(url, new Object[]{userDTO, customTrainingType}, String.class);
    }

    public TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/addTrainingAdditional";
        String jsonRequestBody = objectMapper.writeValueAsString(Map.of(
                "userDTO", userDTO,
                "trainingDTO", trainingDTO,
                "additionalName", additionalName,
                "additionalValue", additionalValue
        ));
        String jsonResponse = restTemplate.postForObject(url, jsonRequestBody, String.class);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/removeTrainingAdditional";
        String jsonRequestBody = objectMapper.writeValueAsString(Map.of(
                "user", userDTO,
                "training", trainingDTO,
                "name", additionalName
        ));
        String jsonResponse = restTemplate.postForObject(url, jsonRequestBody, String.class);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/edit/name";
        String jsonRequestBody = objectMapper.writeValueAsString(Map.of(
                "userDTO", userDTO,
                "trainingDTO", trainingDTO,
                "newName", newName
        ));
        String jsonResponse = restTemplate.postForObject(url, jsonRequestBody, String.class);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/edit/date";
        String jsonRequestBody = objectMapper.writeValueAsString(Map.of(
                "userDTO", userDTO,
                "trainingDTO", trainingDTO,
                "newDate", newDate.toString()
        ));
        String jsonResponse = restTemplate.postForObject(url, jsonRequestBody, String.class);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newDuration) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/edit/duration";
        String jsonRequestBody = objectMapper.writeValueAsString(Map.of(
                "userDTO", userDTO,
                "trainingDTO", trainingDTO,
                "newDuration", newDuration
        ));
        String jsonResponse = restTemplate.postForObject(url, jsonRequestBody, String.class);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newCalories) throws JsonProcessingException, RestClientException {
        String url = rootURL + "/training/edit/calories";
        String jsonRequestBody = objectMapper.writeValueAsString(Map.of(
                "userDTO", userDTO,
                "trainingDTO", trainingDTO,
                "newCalories", newCalories
        ));
        String jsonResponse = restTemplate.postForObject(url, jsonRequestBody, String.class);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }
}
