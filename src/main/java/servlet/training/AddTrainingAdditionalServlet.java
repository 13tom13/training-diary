package servlet.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.initializer.in.ServiceFactory;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoWriteRightsException;
import in.service.training.TrainingService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class AddTrainingAdditionalServlet extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AddTrainingAdditionalServlet() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingService = ServiceFactory.getTrainingService();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = getRequestBody(request);
            // Преобразуем строку запроса в JSONObject
            JSONObject jsonRequest = new JSONObject(requestBody);

            // Извлекаем объекты userDTO и trainingDTO из JSON
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");
            JSONObject trainingJsonObject = jsonRequest.getJSONObject("trainingDTO");
            String additionalNameJsonString = jsonRequest.getString("additionalName");
            String additionalValueJsonString = jsonRequest.getString("additionalValue");

            // Преобразуем JSON объекты в объекты Java с помощью Jackson
            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            TrainingDTO trainingDTO = objectMapper.readValue(trainingJsonObject.toString(), TrainingDTO.class);

            // Пример сохранения тренировки
            trainingDTO = trainingService.addTrainingAdditional(userDTO, trainingDTO, additionalNameJsonString, additionalValueJsonString);
            writeJsonResponse(response, trainingDTO, HttpServletResponse.SC_OK);
        } catch (RepositoryException | NoWriteRightsException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error saving training: " + e.getMessage());
        }
    }
}