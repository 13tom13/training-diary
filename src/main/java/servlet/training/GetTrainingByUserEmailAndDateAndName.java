package servlet.training;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import exceptions.RepositoryException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static servlet.utils.ServletUtils.writeJsonResponse;


public class GetTrainingByUserEmailAndDateAndName extends TrainingServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем все тренировки пользователя через сервис
        try {
            // Получаем JSON-строку из параметров
            String userEmailJson = request.getParameter("userEmail");
            String dateJson = request.getParameter("date");
            String nameJson = request.getParameter("name");
            // Преобразуем JSON в объекты UserDTO
            UserDTO userDTO = objectMapper.readValue(userEmailJson, UserDTO.class);
            String decodedDate = objectMapper.readValue(dateJson, String.class);
            String decodedName = objectMapper.readValue(nameJson, String.class);
            TrainingDTO training = trainingService.getTrainingByUserEmailAndDataAndName(userDTO, decodedDate, decodedName);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, training, HttpServletResponse.SC_OK);
        } catch (RepositoryException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}