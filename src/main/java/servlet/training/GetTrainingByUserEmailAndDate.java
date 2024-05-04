package servlet.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.RepositoryException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TreeSet;

import static servlet.utils.ServletUtils.writeJsonResponse;

public class GetTrainingByUserEmailAndDate extends TrainingServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем все тренировки пользователя через сервис
        try {
            // Получаем JSON-строку из параметров
            String userEmailJson = request.getParameter("userEmail");
            String date = request.getParameter("date");

            UserDTO userDTO = objectMapper.readValue(userEmailJson, UserDTO.class);
            String decodedDate = objectMapper.readValue(date, String.class);
            TreeSet<TrainingDTO> allTraining = trainingService.getTrainingsByUserEmailAndData(userDTO, decodedDate);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, allTraining, HttpServletResponse.SC_OK);
        } catch (RepositoryException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
