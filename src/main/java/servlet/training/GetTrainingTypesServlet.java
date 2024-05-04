package servlet.training;
import config.initializer.ServiceFactory;
import in.service.training.TrainingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.UserDTO;

import java.io.IOException;
import java.util.List;

import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.getObjectMapper;

public class GetTrainingTypesServlet extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper = getObjectMapper();

    public GetTrainingTypesServlet() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingService = ServiceFactory.getTrainingService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Десериализация JSON в объект UserDTO
        String userJson = getJsonParamFromRequest(request, "user");

        // Преобразуем JSON в объект UserDTO
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);

        // Получение списка типов тренировок
        List<String> trainingTypes = trainingService.getTrainingTypes(userDTO);

        // Преобразование списка в JSON и отправка обратно в ответе
        writeJsonResponse(response, trainingTypes, HttpServletResponse.SC_OK);
    }
}

