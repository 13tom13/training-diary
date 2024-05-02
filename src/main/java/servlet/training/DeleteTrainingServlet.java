package servlet.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import in.service.training.TrainingService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import static config.initializer.in.ServiceFactory.getTrainingService;
import static servlet.utils.ServletUtils.getJsonParamFromRequest;
import static utils.Utils.getDateFromString;
import static utils.Utils.getObjectMapper;


public class DeleteTrainingServlet extends HttpServlet {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper = getObjectMapper();

    public DeleteTrainingServlet() {
        this.trainingService = getTrainingService(); // Инициализируйте ваш сервис тренировок здесь
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userJson = getJsonParamFromRequest(request, "user");
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
        String dateString = request.getParameter("date");
        LocalDate date = getDateFromString(dateString);
        System.out.println(dateString);
        System.out.println(date);


        String trainingName = request.getParameter("name");

        try {
            boolean success = trainingService.deleteTraining(userDTO, date, trainingName);
            // Отправка ответа в формате JSON
            response.setContentType("application/json");
            if (success) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT); // Успешное удаление
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Не удалось удалить тренировку
            }
        } catch (RepositoryException | InvalidDateFormatException | NoDeleteRightsException e) {
            throw new RuntimeException(e);
        }


    }
}

