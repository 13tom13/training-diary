package in.controller.training.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import static servlet.utils.ServletUtils.encodeToUrlJson;
import static utils.HTTP.*;
import static utils.Utils.getObjectMapper;
import static utils.Utils.getStringFromDate;

public class TrainingControllerHTTP implements TrainingController {

    private final String rootURL = getRootURL();
    private final String getAllTrainingsServletPath = "/training/alltrainings";
    private final String getTrainingsByUserEmailAndDateServletPath = "/training/gettrainingbyuseremailanddate";
    private final String getTrainingsByUserEmailAndDateAndNameServletPath = "/training/getraining";
    private final String saveTrainingServletPath = "/training/savetraining";
    private final String DeleteTrainingServletPath = "/training/deletetraining";
    private final String getTrainingTypesServletPath = "/training/gettrainingtypes";
    private final String saveTrainingTypesServletPath = "/training/savetrainingtypes";
    private final String addTrainingAdditionalServletPath = "/training/addtrainingadditional";
    private final String removeTrainingAdditionalServletPath = "/training/removetrainingadditiona";
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
    public void deleteTraining(UserDTO userDTO, String date, String name) {
        try {
            String userForDelete = encodeToUrlJson(userDTO);
            String dateForDelete = encodeToUrlJson(date);
            String nameForDelete = encodeToUrlJson(name);
            String urlWithParams = rootURL + DeleteTrainingServletPath + "?user=" + userForDelete + "&date=" + dateForDelete + "&name=" + nameForDelete;
            try {
                sendDeleteRequest(urlWithParams);
            } catch (IOException e) {
                System.err.println("Ошибка при отправке запроса " + e.getMessage());
            }
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при конвертации JSON в строку " + e.getMessage());
        }
    }

    @Override
    public TrainingDTO addTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) {

        String requestURL = rootURL + addTrainingAdditionalServletPath;
        Map<String, Object> combinedMap = new HashMap<>();
        combinedMap.put("userDTO", userDTO);
        combinedMap.put("trainingDTO", trainingDTO);
        combinedMap.put("additionalName", additionalName);
        combinedMap.put("additionalValue", additionalValue);
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
            try {
                String addTrainingAdditional = sendPostRequest(requestURL, jsonRequestBody);
                return objectMapper.readValue(addTrainingAdditional, TrainingDTO.class);
            } catch (IOException e) {
                System.err.println("Ошибка при отправке запроса " + e.getMessage());
                return trainingDTO;
            }
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при конвертации JSON в строку " + e.getMessage());
            return trainingDTO;
        }
    }

    @Override
    public TrainingDTO removeTrainingAdditional(UserDTO userDTO, TrainingDTO trainingDTO, String additionalName) {
        try {
            String userForDelete = encodeToUrlJson(userDTO);
            String trainingForDelete = encodeToUrlJson(trainingDTO);
            String additionalNameForDelete = encodeToUrlJson(additionalName);
            String urlWithParams = rootURL + removeTrainingAdditionalServletPath + "?user=" + userForDelete + "&training=" + trainingForDelete + "&name=" + additionalNameForDelete;
            try {
                int responseCode = sendDeleteRequest(urlWithParams);
                if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                    TrainingDTO training = getTrainingByUserEmailAndDateAndName(userDTO, getStringFromDate(trainingDTO.getDate()), trainingDTO.getName());
                    System.out.println(training);
                    return training;
                } else {
                    System.err.println("Ошибка при отправке запроса " + responseCode);
                    return trainingDTO;
                }
            } catch (IOException e) {
                System.err.println("Ошибка при отправке запроса " + e.getMessage());
                return trainingDTO;
            }
        } catch (JsonProcessingException e) {
            System.err.println("Ошибка при конвертации JSON в строку " + e.getMessage());
            return trainingDTO;
        }
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
            String urlWithParams = rootURL + getAllTrainingsServletPath + "?user=" + encodeToUrlJson(userDTO);

            // Отправляем GET-запрос
            String jsonResponse = sendGetRequest(urlWithParams);

            // Преобразуем JSON в TreeMap<LocalDate, TreeSet<TrainingDTO>>
            TypeReference<TreeMap<LocalDate, TreeSet<TrainingDTO>>> typeRef = new TypeReference<>() {
            };
            return objectMapper.readValue(jsonResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TreeSet<TrainingDTO> getTrainingsByUserEmailAndData(UserDTO userDTO, String trainingDate) {
        try {
            String userEmailForGet = encodeToUrlJson(userDTO.getEmail());
            String dateForGet = encodeToUrlJson(trainingDate);
            String urlWithParams = rootURL + getTrainingsByUserEmailAndDateServletPath + "?userEmail=" + userEmailForGet + "&date=" + dateForGet;
            String jsonResponse = sendGetRequest(urlWithParams);
            TypeReference<TreeSet<TrainingDTO>> typeRef = new TypeReference<>() {
            };
            return objectMapper.readValue(jsonResponse, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TrainingDTO getTrainingByUserEmailAndDateAndName(UserDTO userDTO, String trainingDate, String trainingName) throws IOException {
        String userEmailForGet = encodeToUrlJson(userDTO.getEmail());
        String dateForGet = encodeToUrlJson(trainingDate);
        String nameForGet = encodeToUrlJson(trainingName);
        String urlWithParams = rootURL + getTrainingsByUserEmailAndDateAndNameServletPath +
                               "?userEmail=" + userEmailForGet + "&date=" + dateForGet + "&name=" + nameForGet;
        String jsonResponse = sendGetRequest(urlWithParams);
        return objectMapper.readValue(jsonResponse, TrainingDTO.class);
    }

    @Override
    public List<String> getTrainingTypes(UserDTO userDTO) throws IOException {
        String urlWithParams = rootURL + getTrainingTypesServletPath + "?user=" + URLEncoder.encode(objectMapper.writeValueAsString(userDTO), StandardCharsets.UTF_8);
        String jsonResponse = sendGetRequest(urlWithParams);
        TypeReference<List<String>> typeRef = new TypeReference<>() {
        };
        return objectMapper.readValue(jsonResponse, typeRef);
    }

    @Override
    public void saveTrainingType(UserDTO userDTO, String customTrainingType) throws IOException {
        String requestURL = rootURL + saveTrainingTypesServletPath;
        Map<String, Object> combinedMap = new HashMap<>();
        combinedMap.put("userDTO", userDTO);
        combinedMap.put("customTrainingType", customTrainingType);
        String jsonRequestBody = objectMapper.writeValueAsString(combinedMap);
        String savedTraining = sendPostRequest(requestURL, jsonRequestBody);
        System.out.println(savedTraining);

    }
}
