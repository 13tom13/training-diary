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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class SaveTrainingServlet extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(SaveTrainingServlet.class);

    public SaveTrainingServlet() {
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
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            // Преобразуем строку запроса в JSONObject
            JSONObject jsonRequest = new JSONObject(requestBody.toString());

            // Извлекаем объекты userDTO и trainingDTO из JSON
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");
            JSONObject trainingJsonObject = jsonRequest.getJSONObject("trainingDTO");

            // Преобразуем JSON объекты в объекты Java с помощью Jackson
            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            TrainingDTO trainingDTO = objectMapper.readValue(trainingJsonObject.toString(), TrainingDTO.class);

            // Пример сохранения тренировки
            trainingDTO = trainingService.saveTraining(userDTO, trainingDTO);
            logger.info("Training saved by user: {}", userDTO.getEmail());
            String jsonResponse = objectMapper.writeValueAsString(trainingDTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse);
        } catch (InvalidDateFormatException | RepositoryException | NoWriteRightsException e) {
            logger.error("Error saving training", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error saving training: " + e.getMessage());
        }
    }


}
