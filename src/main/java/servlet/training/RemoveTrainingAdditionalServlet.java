package servlet.training;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import entities.model.Training;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import in.service.training.TrainingService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static config.initializer.ServiceFactory.getTrainingService;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.*;

public class RemoveTrainingAdditionalServlet extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper = getObjectMapper();

    public RemoveTrainingAdditionalServlet() {
        this.trainingService = getTrainingService(); // Инициализируйте ваш сервис тренировок здесь
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String userJson = request.getParameter("user");
        String trainingJson = request.getParameter("training");
        String additionalNameJson = request.getParameter("name");

        try {
            UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
            TrainingDTO TrainingDTO = objectMapper.readValue(trainingJson, TrainingDTO.class);
            String decodedAdditionalName = objectMapper.readValue(additionalNameJson, String.class);
            try {
                trainingService.removeTrainingAdditional(userDTO, TrainingDTO, decodedAdditionalName);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (RepositoryException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (NoEditRightsException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
