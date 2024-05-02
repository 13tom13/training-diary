package servlet.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.UserDTO;
import in.service.training.TrainingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;
import java.util.TreeSet;

import entities.dto.TrainingDTO;

import static config.initializer.in.ServiceFactory.getTrainingService;
import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class GetAllTrainings extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper;

    public GetAllTrainings() {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Получаем JSON-строку из параметра "user"
        String userJson = getJsonParamFromRequest(request, "user");

        // Преобразуем JSON в объект UserDTO
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
        // Получаем все тренировки пользователя через сервис
        TreeMap<Date, TreeSet<TrainingDTO>> allTraining = trainingService.getAllTrainings(userDTO);

        // Преобразуем данные в JSON и отправляем как ответ
       writeJsonResponse(response, allTraining, HttpServletResponse.SC_OK);
    }
}
