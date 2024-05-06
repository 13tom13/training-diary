package servlet.training.statistics;

import entities.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;

import java.time.LocalDate;

public class GetAllTrainingStatisticsPerPeriodServlet extends TrainingStatisticsServlet {

    @Override
    protected int getResult(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException {
        return trainingStatisticsService.getAllTrainingStatisticsPerPeriod(userDTO, startDate, endDate);
    }
}
