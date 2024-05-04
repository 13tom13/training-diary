package servlet.training;

import com.fasterxml.jackson.core.JsonProcessingException;
import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.RepositoryException;
import exceptions.security.rights.NoEditRightsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RemoveTrainingAdditionalServlet extends TrainingServlet {

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
