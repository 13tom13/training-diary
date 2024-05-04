package servlet.training;

import entities.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

import static servlet.utils.ServletUtils.getRequestBody;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class SaveTrainingTypeServlet extends TrainingServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String requestBody = getRequestBody(request);

            // Преобразуем строку запроса в JSONObject
            JSONObject jsonRequest = new JSONObject(requestBody);

            // Извлекаем объекты userDTO и trainingDTO из JSON
            JSONObject userJsonObject = jsonRequest.getJSONObject("userDTO");
            JSONObject trainingJsonObject = jsonRequest.getJSONObject("customTrainingType");

            // Преобразуем JSON объекты в объекты Java с помощью Jackson
            UserDTO userDTO = objectMapper.readValue(userJsonObject.toString(), UserDTO.class);
            String customTrainingType = trainingJsonObject.toString();

            // Пример сохранения тренировки
            trainingService.saveTrainingType(userDTO, customTrainingType);
            writeJsonResponse(response, customTrainingType, HttpServletResponse.SC_OK);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error saving training: " + e.getMessage());
        }

    }
}
