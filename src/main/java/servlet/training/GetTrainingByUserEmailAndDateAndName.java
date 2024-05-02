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
import java.text.ParseException;
import java.util.Date;

import static config.initializer.in.ServiceFactory.getTrainingService;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.formatDate;

public class GetTrainingByUserEmailAndDateAndName extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper;

    public GetTrainingByUserEmailAndDateAndName() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingService = getTrainingService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Создаем экземпляр сервиса
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем все тренировки пользователя через сервис
        try {
            // Получаем JSON-строку из параметров
            String userEmailJson = request.getParameter("userEmail");
            String date = request.getParameter("date");
            String name = request.getParameter("name");
            // Преобразуем JSON в объекты UserDTO
            UserDTO userDTO = objectMapper.readValue(userEmailJson, UserDTO.class);
            Date parsedDate = formatDate(date);
            TrainingDTO allTraining = trainingService.getTrainingByUserEmailAndDataAndName(userDTO, parsedDate, name);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, allTraining, HttpServletResponse.SC_OK);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}