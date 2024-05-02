package in.controller.training.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.TrainingController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static config.ApplicationConfig.getRootURL;
import static utils.HTTP.*;
import static utils.Utils.getObjectMapper;

public class TrainingControllerHTTP implements TrainingController {

    private final String rootURL = getRootURL();
    private final String getAllTrainingsServletPath = "/training/alltrainings";
    private final String getTrainingsByUserEmailAndDate = "/training/gettrainingbyuseremailanddate";
    private final String getTrainingsByUserEmailAndDateAndNameServletPath = "/training/getraining";
    private final String saveTrainingServletPath = "/training/savetraining";
    private final String getTrainingTypesServletPath = "/training/gettrainingtypes";
    private final String saveTrainingTypesServletPath = "/training/savetrainingtypes";
    private final String DeleteTrainingServletPath = "/training/deletetraining";
    private final String addTrainingAdditionalServletPath = "/training/addtrainingadditional";
    private final ObjectMapper objectMapper = getObjectMapper();

    @Override
    public TrainingDTO saveTraining(UserDTO userDTO, TrainingDTO trainingDTO) throws InvalidDateFormatException, NoWriteRightsException, RepositoryException {
        try {
            String requestURL = rootURL + saveTrainingServletPath;
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("trainingDTO", trainingDTO);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String savedTraining = sendPostRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(savedTraining, TrainingDTO.class);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public boolean deleteTraining(UserDTO userDTO, LocalDate date, String name) {
        try {
            String userForDelete = URLEncoder.encode(objectMapper.writeValueAsString(userDTO), StandardCharsets.UTF_8);
            String dateForDelete = objectMapper.writeValueAsString(date);
            String nameForDelete = URLEncoder.encode(objectMapper.writeValueAsString(name), StandardCharsets.UTF_8);

            String urlWithParams = rootURL + DeleteTrainingServletPath + "?user=" + userForDelete + "&date=" + dateForDelete + "&name=" + nameForDelete;
            int statusCode = sendDeleteRequest(urlWithParams);

            // Если статус код равен 204 (HTTP_NO_CONTENT), значит удаление успешно
            return statusCode == HttpURLConnection.HTTP_NO_CONTENT;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) {
        try {
            String requestURL = rootURL + addTrainingAdditionalServletPath;
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("trainingDTO", trainingDTO);
            combinedMap.put("additionalName", additionalName);
            combinedMap.put("additionalValue", additionalValue);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String addTrainingAdditional = sendPostRequest(requestURL, jsonRequestBody);
            return objectMapper.readValue(addTrainingAdditional, TrainingDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName) {
        return null;
    }

    @Override
    public TrainingDTO changeNameTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newName) {
        return null;
    }

    @Override
    public TrainingDTO changeDateTraining(UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate) {
        return null;
    }

    @Override
    public TrainingDTO changeDurationTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newDuration) {
        return null;
    }

    @Override
    public TrainingDTO changeCaloriesTraining(UserDTO userDTO, TrainingDTO trainingDTO, String newCalories) {
        return null;
    }

    @Override
    public TreeMap<LocalDate, TreeSet<TrainingDTO>> getAllTrainings(UserDTO userDTO) {
        try {
            // Формируем URL запроса с параметрами
            String urlWithParams = rootURL + getAllTrainingsServletPath + "?user=" + URLEncoder.encode(objectMapper.writeValueAsString(userDTO), StandardCharsets.UTF_8);

            // Отправляем GET-запрос
            String jsonResponse = sendGetRequest(urlWithParams);

            // Преобразуем JSON в TreeMap<Date, TreeSet<TrainingDTO>>
            TypeReference<TreeMap<LocalDate, TreeSet<TrainingDTO>>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(jsonResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, LocalDate trainingDate) {
        try {
            String userEmailForGet = URLEncoder.encode(objectMapper.writeValueAsString(userDTO.getEmail()), StandardCharsets.UTF_8);
            String dateForGet = URLEncoder.encode(objectMapper.writeValueAsString(trainingDate), StandardCharsets.UTF_8);

            String urlWithParams = rootURL + getTrainingsByUserEmailAndDate + "?userEmail=" + userEmailForGet + "&date=" + dateForGet;
            String jsonResponse = sendGetRequest(urlWithParams);
            TypeReference<TreeSet<TrainingDTO>> typeRef = new TypeReference<>(){};
            return objectMapper.readValue(jsonResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TrainingDTO getTrainingByUserEmailAndDateAndName(UserDTO userDTO, LocalDate trainingDate, String trainingName) {
        try {
            String userEmailForGet = URLEncoder.encode(objectMapper.writeValueAsString(userDTO.getEmail()), StandardCharsets.UTF_8);
            String dateForGet = URLEncoder.encode(objectMapper.writeValueAsString(trainingDate), StandardCharsets.UTF_8);
            String nameForGet = URLEncoder.encode(objectMapper.writeValueAsString(trainingName), StandardCharsets.UTF_8);

            String urlWithParams = rootURL + getTrainingsByUserEmailAndDateAndNameServletPath +
                                   "?userEmail=" + userEmailForGet + "&date=" + dateForGet + "&name=" + nameForGet;
            String jsonResponse = sendGetRequest(urlWithParams);
            return objectMapper.readValue(jsonResponse, TrainingDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getTrainingTypes(UserDTO userDTO) {
        try {
            String urlWithParams = rootURL + getTrainingTypesServletPath + "?user=" + URLEncoder.encode(objectMapper.writeValueAsString(userDTO), StandardCharsets.UTF_8);
            String jsonResponse = sendGetRequest(urlWithParams);
            TypeReference<List<String>> typeRef = new TypeReference<>(){};
            return objectMapper.readValue(jsonResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveTrainingType(UserDTO userDTO, String customTrainingType) {
        try {
            String requestURL = rootURL + saveTrainingTypesServletPath;
            Map<String, Object> combinedMap = new HashMap<>();
            combinedMap.put("userDTO", userDTO);
            combinedMap.put("customTrainingType", customTrainingType);
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            String savedTraining = sendPostRequest(requestURL, jsonRequestBody);
            System.out.println(savedTraining);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
