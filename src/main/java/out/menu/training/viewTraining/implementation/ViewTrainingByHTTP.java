package out.menu.training.viewTraining.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import out.menu.training.viewTraining.ViewTraining;

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
import static utils.HTTP.sendGetRequest;
import static utils.Utils.printAllTraining;

/**
 * Представляет класс для просмотра тренировок пользователя через HTTP.
 */
public class ViewTrainingByHTTP implements ViewTraining {

    private final String rootUrl = getRootURL();
    private final String servletPath = "/training/alltrainings";
    private final ObjectMapper objectMapper = new ObjectMapper();;

    public ViewTrainingByHTTP() {
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



}

