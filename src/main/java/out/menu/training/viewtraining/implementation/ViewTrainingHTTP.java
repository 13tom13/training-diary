package out.menu.training.viewtraining.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import out.menu.training.viewtraining.ViewTraining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

import static config.ApplicationConfig.getRootURL;
import static utils.Utils.printAllTraining;

/**
 * Представляет класс для просмотра тренировок пользователя через HTTP.
 */
public class ViewTrainingHTTP implements ViewTraining {

    private final String rootUrl = getRootURL();
    private final String servletPath = "/training/alltrainings";
    private final ObjectMapper objectMapper;

    public ViewTrainingHTTP() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     * @throws RuntimeException если возникает ошибка ввода-вывода при отправке запроса или получении ответа.
     */
    public void viewAllTraining(UserDTO userDTO) {
        try {
            // Формируем URL запроса с параметрами
            String urlWithParams = rootUrl +  servletPath + "?user=" + URLEncoder.encode(objectMapper.writeValueAsString(userDTO), StandardCharsets.UTF_8);

            // Отправляем GET-запрос
            String jsonResponse = sendGetRequest(urlWithParams);

            // Преобразуем JSON в TreeMap<Date, TreeSet<TrainingDTO>>
            TypeReference<TreeMap<Date, TreeSet<TrainingDTO>>> typeRef = new TypeReference<>() {};
            TreeMap<Date, TreeSet<TrainingDTO>> allTraining = objectMapper.readValue(jsonResponse, typeRef);

            // Выводим тренировки на экран
            printAllTraining(allTraining);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет GET-запрос по указанному URL и возвращает ответ в виде строки.
     *
     * @param urlWithParams URL с параметрами запроса.
     * @return Ответ на GET-запрос в виде строки.
     * @throws IOException если возникает ошибка ввода-вывода при отправке запроса или получении ответа.
     */
    private String sendGetRequest(String urlWithParams) throws IOException {
        URL url = new URL(urlWithParams);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            } finally {
                connection.disconnect();
            }
        } else {
            throw new IOException("Ошибка при получении данных: " + statusCode);

        }
    }

}

