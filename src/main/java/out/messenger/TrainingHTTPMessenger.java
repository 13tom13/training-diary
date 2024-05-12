package out.messenger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrainingHTTPMessenger {

    private final String rootURL = "http://localhost:8080/training-diary";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public TreeMap<LocalDate, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) throws RestClientException, JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/all")
                .queryParam("email", userDTO.getEmail())
                .toUriString();
        try {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            TypeReference<TreeMap<LocalDate, TreeSet<TrainingDTO>>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(forEntity.getBody(), typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, String trainingDate) throws RestClientException, JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/get/byDate")
                .queryParam("email", userDTO.getEmail())
                .queryParam("date", trainingDate)
                .toUriString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();
        TypeReference<TreeSet<TrainingDTO>> typeRef = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonResponse, typeRef);
    }

    public TrainingDTO getTrainingByUserEmailAndDateAndName(UserDTO userDTO, String trainingDate, String trainingName) throws RestClientException, JsonProcessingException {
        System.out.println("getTrainingByUserEmailAndDateAndName");
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/get")
                .queryParam("email", userDTO.getEmail())
                .queryParam("date", trainingDate)
                .queryParam("name", trainingName)
                .toUriString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    public List<String> getTrainingTypes(UserDTO userDTO) throws RestClientException, JsonProcessingException {
        System.out.println("getTrainingTypes");
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/type/get")
                .queryParam("id", userDTO.getId())
                .toUriString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();
        TypeReference<List<String>> typeRef = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonResponse, typeRef);
    }

    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws RestClientException, JsonProcessingException{
        String url = rootURL + "/training/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(new TrainingRequest(userDTO, trainingDTO), headers);
        ResponseEntity<TrainingDTO> responseEntity = restTemplate.postForEntity(url, requestEntity, TrainingDTO.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RestClientException(responseEntity.getStatusCode().toString());
        }
        return responseEntity.getBody();
    }

    public void deleteTraining(UserDTO userDTO, String date, String name) throws RestClientException, JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/delete")
                .queryParam("email",userDTO.getEmail())
                .queryParam("date", date)
                .queryParam("name", name)
                .toUriString();
        restTemplate.delete(url);
    }

    public void saveTrainingType(UserDTO userDTO, String customTrainingType)
            throws RestClientException, JsonProcessingException {
        System.out.println("saveTrainingType messenger: " + customTrainingType);
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/type/save")
                .queryParam("id",userDTO.getId())
                .queryParam("type",customTrainingType)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(customTrainingType), headers);
        restTemplate.postForEntity(url, requestEntity, String.class);
    }

    public TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue)
            throws JsonProcessingException, RestClientException {
        System.out.println("addTrainingAdditional messenger");
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/addition/add")
                .queryParam("name",additionalName)
                .queryParam("value", additionalValue)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(new TrainingRequest(userDTO, trainingDTO), headers);
        ResponseEntity<TrainingDTO> responseEntity = restTemplate.postForEntity(url, requestEntity, TrainingDTO.class);
        return responseEntity.getBody();
    }

    public TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName)
            throws JsonProcessingException, RestClientException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/addition/remove")
                .queryParam("email",userDTO.getEmail())
                .queryParam("name", additionalName)
                .toUriString();
        String jsonRequestBody = objectMapper.writeValueAsString(trainingDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<TrainingDTO> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, TrainingDTO.class);
        return responseEntity.getBody();
    }

    public TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName) throws JsonProcessingException, RestClientException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/edit/name")
                .queryParam("email",userDTO.getEmail())
                .queryParam("name", newName)
                .toUriString();
        return getTrainingDTOFromPUT(trainingDTO, url);
    }

    public TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate) throws JsonProcessingException, RestClientException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/edit/date")
                .queryParam("email",userDTO.getEmail())
                .queryParam("date", newDate)
                .toUriString();
        return getTrainingDTOFromPUT(trainingDTO, url);
    }

    public TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newDuration) throws JsonProcessingException, RestClientException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/edit/duration")
                .queryParam("email",userDTO.getEmail())
                .queryParam("duration", newDuration)
                .toUriString();
        return getTrainingDTOFromPUT(trainingDTO, url);
    }

    public TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, int newCalories) throws JsonProcessingException, RestClientException {
        String url = UriComponentsBuilder.fromHttpUrl(rootURL)
                .path("/training/edit/calories")
                .queryParam("email",userDTO.getEmail())
                .queryParam("calories", newCalories)
                .toUriString();
        return getTrainingDTOFromPUT(trainingDTO, url);
    }

    @Nullable
    private TrainingDTO getTrainingDTOFromPUT(TrainingDTO trainingDTO, String url) throws JsonProcessingException {
        String jsonRequestBody = objectMapper.writeValueAsString(trainingDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<TrainingDTO> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, TrainingDTO.class);
        return responseEntity.getBody();
    }
}
