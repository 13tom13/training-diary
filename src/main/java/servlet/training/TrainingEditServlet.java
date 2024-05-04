package servlet.training;

import entities.dto.TrainingDTO;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoEditRightsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.getDateFromString;

public class TrainingEditServlet extends TrainingServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = getRequestBody(request);
            JSONObject jsonRequest = new JSONObject(requestBody);
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");
            JSONObject trainingJsonObject = jsonRequest.getJSONObject("trainingDTO");

            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            TrainingDTO trainingDTO = objectMapper.readValue(trainingJsonObject.toString(), TrainingDTO.class);

            String[] pathParts = request.getPathInfo().split("/");

            if (pathParts.length >= 2) {
                String editType = pathParts[1];
                switch (editType) {
                    case "calories":
                        int newCalories = jsonRequest.getInt("newCalories");
                        trainingDTO = trainingService.changeCaloriesTraining(userDTO, trainingDTO, newCalories);
                        break;
                    case "date":
                        LocalDate newDate = getDateFromString(jsonRequest.getString("newDate"));
                        trainingDTO = trainingService.changeDateTraining(userDTO, trainingDTO, newDate);
                        break;
                    case "duration":
                        int newDuration = jsonRequest.getInt("newDuration");
                        trainingDTO = trainingService.changeDurationTraining(userDTO, trainingDTO, newDuration);
                        break;
                    case "name":
                        String newName = jsonRequest.getString("newName");
                        trainingDTO = trainingService.changeNameTraining(userDTO, trainingDTO, newName);
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Не поддерживаемый тип изменения");
                        return;
                }
            } else {
                // Если не указан тип изменения, отправляем ошибку
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Не указан тип изменения");
                return;
            }

            writeJsonResponse(response, trainingDTO, HttpServletResponse.SC_OK);
        } catch (RepositoryException | JSONException e) {
            int statusCode = (e instanceof RepositoryException) ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : HttpServletResponse.SC_BAD_REQUEST;
            response.setStatus(statusCode);
            response.getWriter().write("Ошибка при обработке запроса: " + e.getMessage());
        } catch (NoEditRightsException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Нет прав для изменения: " + e.getMessage());
        } catch (InvalidDateFormatException e) {
            response.getWriter().write("Ошибка при обработке запроса: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
