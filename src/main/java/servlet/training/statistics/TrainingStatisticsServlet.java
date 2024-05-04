package servlet.training.statistics;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.initializer.ServiceFactory;
import entities.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;
import in.service.training.TrainingStatisticsService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static servlet.utils.ServletUtils.writeJsonResponse;
import static utils.Utils.getDateFromString;
import static utils.Utils.getObjectMapper;

public abstract class TrainingStatisticsServlet extends HttpServlet {

    protected final TrainingStatisticsService trainingStatisticsService;
    protected final ObjectMapper objectMapper;
    protected final static int ERROR_RESPONSE = -1;

    protected TrainingStatisticsServlet() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingStatisticsService = ServiceFactory.getTrainingStatisticsService();
            this.objectMapper = getObjectMapper();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Получаем JSON-строку из параметра
        String userJson = getJsonParamFromRequest(request, "user");
        String startDateJson = getJsonParamFromRequest(request, "startdate");
        String endDateJson = getJsonParamFromRequest(request, "enddate");

        // Преобразуем JSON в объекты
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
        LocalDate startDate = getDateFromString(startDateJson);
        LocalDate endDate = getDateFromString(endDateJson);

        try {
            int result = getResult(userDTO, startDate, endDate);
            // Преобразуем данные в JSON и отправляем как ответ
            writeJsonResponse(response, result, HttpServletResponse.SC_OK);
        } catch (NoStatisticsRightsException e) {
            writeJsonResponse(response, ERROR_RESPONSE, HttpServletResponse.SC_FORBIDDEN);
        }
    }

    protected abstract int getResult(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException;
}
