package servlet.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.RepositoryException;
import in.service.training.TrainingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeSet;

import static config.initializer.in.ServiceFactory.getTrainingService;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.getDateFromString;
import static utils.Utils.getObjectMapper;

public class GetTrainingByUserEmailAndDate extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper=getObjectMapper();

    public GetTrainingByUserEmailAndDate() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingService = getTrainingService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем все тренировки пользователя через сервис
        try {
            // Получаем JSON-строку из параметров
            String userEmailJson = request.getParameter("userEmail");
            String date = request.getParameter("date");
            // Преобразуем JSON в объекты UserDTO
            UserDTO userDTO = objectMapper.readValue(userEmailJson, UserDTO.class);
            LocalDate parsedDate = getDateFromString(date);
            TreeSet<TrainingDTO> allTraining = trainingService.getTrainingsByUserEmailAndData(userDTO, parsedDate);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, allTraining, HttpServletResponse.SC_OK);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
