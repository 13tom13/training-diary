package servlet.training.statistics;

import entity.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static servlet.utils.ServletUtils.writeJsonResponse;

public class GetAllTrainingStatisticsServlet extends TrainingStatisticsServlet {

    public GetAllTrainingStatisticsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем JSON-строку из параметра "user"
        String userJson = getJsonParamFromRequest(request, "user");

        // Преобразуем JSON в объект UserDTO
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);

        try {
            int result = trainingStatisticsService.getAllTrainingStatistics(userDTO);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, result, HttpServletResponse.SC_OK);
        } catch (NoStatisticsRightsException e) {
            writeJsonResponse(response, ERROR_RESPONSE, HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected int getResult(UserDTO userDTO, LocalDate startDate, LocalDate endDate)  {
        return 0;
    }
}
