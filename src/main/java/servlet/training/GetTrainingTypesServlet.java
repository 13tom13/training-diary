package servlet.training;

import entity.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class GetTrainingTypesServlet extends TrainingServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

