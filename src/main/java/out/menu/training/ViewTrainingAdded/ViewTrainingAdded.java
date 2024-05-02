package out.menu.training.ViewTrainingAdded;

import entities.dto.TrainingDTO;

public interface ViewTrainingAdded {

    void addTraining();

    String chooseTrainingType();

    void deleteTraining();

    void addTrainingAdditional(TrainingDTO trainingDTO);
}
