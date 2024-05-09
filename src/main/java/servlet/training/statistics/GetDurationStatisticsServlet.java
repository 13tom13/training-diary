package servlet.training.statistics;

import entity.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;

import java.time.LocalDate;

public class GetDurationStatisticsServlet extends TrainingStatisticsServlet {

    public GetDurationStatisticsServlet() {
        super();
    }

    @Override
    protected int getResult(UserDTO userDTO, LocalDate startDate, LocalDate endDate) throws NoStatisticsRightsException {
        return trainingStatisticsService.getDurationStatisticsPerPeriod(userDTO, startDate, endDate);
    }
}
