package out.messengers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.UserDTO;
import in.controller.training.statistics.TrainingStatisticsController;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;

import static config.ApplicationConfig.getRootURL;
import static utils.ServletUtils.encodeToUrlJson;
import static utils.HTTP.sendGetRequest;
import static utils.ServletUtils.getObjectMapper;
import static utils.Utils.getStringFromDate;

public class TrainingStatisticsMessenger {

    public TrainingStatisticsMessenger() {
    }

    private final String rootURL = getRootURL();
    private final static String getAllTrainingStatisticsServletPath = "/trainingstatistics/getalltrainingstatistics";
    private final static String getAllTrainingStatisticsPerPeriodServletPath = "/trainingstatistics/statisticsperperiod";
    private final static String getDurationStatisticsPerPeriodServletPath = "/trainingstatistics/statisticsduration";
    private final static String getCaloriesStatisticsPerPeriodServletPath = "/trainingstatistics/statisticscalories";

    private final ObjectMapper objectMapper = getObjectMapper();


//    @Override
//    public ResponseEntity<?> getAllTrainingStatistics(UserDTO userDTO) {
//        try {
//            // Формируем URL запроса с параметрами
//            String urlWithParams = rootURL + getAllTrainingStatisticsServletPath + "?user=" + encodeToUrlJson(userDTO);
//
//            // Отправляем GET-запрос
//            String jsonResponse = sendGetRequest(urlWithParams);
//
//            return objectMapper.readValue(jsonResponse, Integer.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public ResponseEntity<?> getAllTrainingStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
//        return getStatisticPerPeriod(getAllTrainingStatisticsPerPeriodServletPath, userDTO, startDate, endDate);
//    }
//
//    @Override
//    public ResponseEntity<?> getDurationStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
//        return getStatisticPerPeriod(getDurationStatisticsPerPeriodServletPath, userDTO, startDate, endDate);
//    }
//
//    @Override
//    public ResponseEntity<?> getCaloriesBurnedPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
//        return getStatisticPerPeriod(getCaloriesStatisticsPerPeriodServletPath, userDTO, startDate, endDate);
//    }
//
//    private Integer getStatisticPerPeriod(String Url, UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
//        try {
//            // Формируем URL запроса с параметрами
//            String stringUserDTO = encodeToUrlJson(userDTO);
//            String stringStartDate = getStringFromDate(startDate);
//            String stringEndDate = getStringFromDate(endDate);
//            String urlWithParams = rootURL + Url + "?user="
//                                   + stringUserDTO + "&startdate=" + stringStartDate + "&enddate=" + stringEndDate;
//            // Отправляем GET-запрос
//            String jsonResponse = sendGetRequest(urlWithParams);
//            return objectMapper.readValue(jsonResponse, Integer.class);
//        } catch (IOException e) {
//            System.err.println("Ошибка при обработке данных: " + e.getMessage());
//            return -1;
//        }
//    }
}
