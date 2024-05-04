package servlet.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.initializer.ServiceFactory;
import in.service.training.TrainingService;
import jakarta.servlet.http.HttpServlet;

import static utils.Utils.getObjectMapper;

public abstract class TrainingServlet extends HttpServlet {

    protected final TrainingService trainingService;
    protected final ObjectMapper objectMapper;

    public TrainingServlet() {
        try {
            Class.forName("org.postgresql.Driver");
            this.trainingService = ServiceFactory.getTrainingService();
            this.objectMapper = getObjectMapper();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
