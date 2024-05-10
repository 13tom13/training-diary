package out.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TrainingStatisticsHTTPMessenger {

    private final String rootURL = "http://localhost:8080/training-diary";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public Integer getAllTrainingStatistics(UserDTO userDTO) {
        try {
            String user = objectMapper.writeValueAsString(userDTO);
            String url = rootURL + "/statistics/all" + "?user=" + user;
            String jsonResponse = restTemplate.getForObject(url, String.class);

            return objectMapper.readValue(jsonResponse, Integer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getAllTrainingStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        try {
            // Формируем URL запроса с параметрами
            String url = rootURL + "/statistics/period"
                         + "?user=" + objectMapper.writeValueAsString(userDTO)
                         + "&start=" + startDate.toString()
                         + "&end=" + endDate.toString();
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(jsonResponse, Integer.class);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке данных: " + e.getMessage());
            return -1;
        }
    }

    public Integer getDurationStatisticsPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        try {
            // Формируем URL запроса с параметрами
            String url = rootURL + "/statistics/period/duration"
                         + "?user="  + objectMapper.writeValueAsString(userDTO)
                         + "&start=" + startDate.toString()
                         + "&end="   + endDate.toString();
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(jsonResponse, Integer.class);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке данных: " + e.getMessage());
            return -1;
        }
    }

    public Integer getCaloriesBurnedPerPeriod(UserDTO userDTO, LocalDate startDate, LocalDate endDate) {
        try {
            String url = rootURL + "/statistics/period/calories"
                         + "?user="  + objectMapper.writeValueAsString(userDTO)
                         + "&start=" + startDate.toString()
                         + "&end="   + endDate.toString();
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(jsonResponse, Integer.class);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке данных: " + e.getMessage());
            return -1;
        }
    }
}
