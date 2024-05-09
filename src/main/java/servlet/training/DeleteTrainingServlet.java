package servlet.training;

import com.fasterxml.jackson.core.JsonProcessingException;
import entity.dto.UserDTO;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DeleteTrainingServlet extends TrainingServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String userJson = request.getParameter("user");
        String dateString = request.getParameter("date");
        String trainingName = request.getParameter("name");
        try {
            UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
            String decodedDate = objectMapper.readValue(dateString, String.class);
            String decodedTrainingName = objectMapper.readValue(trainingName, String.class);
            try {
                trainingService.deleteTraining(userDTO, decodedDate, decodedTrainingName);
            } catch (RepositoryException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } catch (NoDeleteRightsException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}

