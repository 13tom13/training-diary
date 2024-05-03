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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static config.initializer.in.ServiceFactory.getTrainingService;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.getDateFromString;
import static utils.Utils.getObjectMapper;


public class GetTrainingByUserEmailAndDateAndName extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper=getObjectMapper();

    public GetTrainingByUserEmailAndDateAndName() {
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
            String name = request.getParameter("name");
            // Преобразуем JSON в объекты UserDTO
            UserDTO userDTO = objectMapper.readValue(userEmailJson, UserDTO.class);
            String decodedDate = URLDecoder.decode(date.replaceAll("\"", ""), StandardCharsets.UTF_8);
            TrainingDTO training = trainingService.getTrainingByUserEmailAndDataAndName(userDTO, decodedDate, name);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, training, HttpServletResponse.SC_OK);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}