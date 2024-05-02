package servlet.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.UserDTO;
import in.service.training.TrainingService;
import in.service.training.implementation.TrainingServiceImpl;
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

public class ViewTrainingServlet extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper;

    public ViewTrainingServlet() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingService = getTrainingService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Создаем экземпляр сервиса
        this.objectMapper = new ObjectMapper();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Получаем JSON-строку атрибута "user" из сессии
        String userJson = request.getParameter("user");

        // Проверяем, есть ли атрибут "userJson" в сессии
        if (userJson == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Пользователь не аутентифицирован");
            return;
        }

        // Преобразуем JSON в объект UserDTO
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
        // Получаем все тренировки пользователя через сервис
        TreeMap<Date, TreeSet<TrainingDTO>> allTraining = trainingService.getAllTrainings(userDTO);

        // Преобразуем данные в JSON и отправляем как ответ
        String jsonResponse = objectMapper.writeValueAsString(allTraining);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK); // 200
        response.getWriter().write(jsonResponse);
    }
}
