package servlet.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.TreeSet;

import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class GetAllTrainings extends TrainingServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем JSON-строку из параметра "user"
        String userJson = getJsonParamFromRequest(request, "user");

        // Преобразуем JSON в объект UserDTO
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
        // Получаем все тренировки пользователя через сервис
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining = trainingService.getAllTrainings(userDTO);

        // Преобразуем данные в JSON и отправляем как ответ
        writeJsonResponse(response, allTraining, HttpServletResponse.SC_OK);
    }
}
