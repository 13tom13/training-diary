package out.menu.training.viewtraining.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import out.menu.training.viewtraining.ViewTraining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static config.ApplicationConfig.getApplicationURL;
import static utils.Utils.getFormattedDate;

/**
 * Представляет класс для просмотра тренировок пользователя через HTTP.
 */
public class ViewTrainingHTTP implements ViewTraining {

    private final String url = getApplicationURL();
    private final ObjectMapper objectMapper;

    /**
     * Создает экземпляр ViewTrainingHTTP с заданным URL сервлета.
     */
    public ViewTrainingHTTP() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     * @throws IOException если возникает ошибка ввода-вывода при отправке запроса или получении ответа.
     */
    public void viewAllTraining(UserDTO userDTO) {
        try {
            // Формируем URL запроса с параметрами
            String urlWithParams = url + "/training/alltrainings";

            // Создаем объект URL
            URL url = new URL(urlWithParams);

            // Открываем соединение
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Получаем ответ от сервера
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                // Читаем ответ сервера
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Преобразуем ответ в JSON и далее в TreeMap<Date, TreeSet<TrainingDTO>>
                    String jsonResponse = response.toString();
                    TreeMap<Date, TreeSet<TrainingDTO>> allTraining = objectMapper.readValue(jsonResponse, TreeMap.class);

                    // Выводим тренировки на экран
                    if (allTraining.isEmpty()) {
                        System.out.println("Список тренировок пуст");
                        return;
                    }

                    for (Map.Entry<Date, TreeSet<TrainingDTO>> entry : allTraining.entrySet()) {
                        String currentDate = getFormattedDate(entry.getKey());
                        TreeSet<TrainingDTO> trainingsOnDate = entry.getValue();

                        System.out.println("\n" + "=====" + currentDate + "=====");

                        for (TrainingDTO training : trainingsOnDate) {
                            System.out.println(training);
                            System.out.println("--------------------------------------------------");
                        }
                    }
                }
            } else {
                System.out.println("Ошибка при получении данных: " + statusCode);
            }

            // Закрываем соединение
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
