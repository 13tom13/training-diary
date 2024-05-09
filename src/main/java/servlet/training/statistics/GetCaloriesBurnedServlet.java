package servlet.training.statistics;

import entity.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;

import java.time.LocalDate;

public class GetCaloriesBurnedServlet extends TrainingStatisticsServlet {

    public GetCaloriesBurnedServlet() {
        super();
    }

    @Override
    protected int getResult(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException {
        return trainingStatisticsService.getCaloriesBurnedPerPeriod(userDTO, startDate, endDate);
    }
}
