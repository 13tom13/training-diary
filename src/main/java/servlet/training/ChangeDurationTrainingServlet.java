package servlet.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.RepositoryException;
import exceptions.security.rights.NoEditRightsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class ChangeDurationTrainingServlet extends TrainingServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = getRequestBody(request);

            // Преобразуем строку запроса в JSONObject
            JSONObject jsonRequest = new JSONObject(requestBody);

            // Извлекаем объекты userDTO и trainingDTO из JSON
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");
            JSONObject trainingJsonObject = jsonRequest.getJSONObject("trainingDTO");


            // Преобразуем JSON объекты в объекты Java с помощью Jackson
            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            TrainingDTO trainingDTO = objectMapper.readValue(trainingJsonObject.toString(), TrainingDTO.class);
            int newDuration = jsonRequest.getInt("newDuration");

            // Пример сохранения тренировки
            try {
                trainingDTO = trainingService.changeDurationTraining(userDTO, trainingDTO, newDuration);
            } catch (NoEditRightsException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            writeJsonResponse(response, trainingDTO, HttpServletResponse.SC_OK);
        } catch (RepositoryException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error saving training: " + e.getMessage());
        }
    }
}