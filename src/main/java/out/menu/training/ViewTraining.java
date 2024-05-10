package out.menu.training;


import com.fasterxml.jackson.core.JsonProcessingException;
import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import out.messenger.TrainingHTTPMessenger;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static utils.Utils.getStringFromDate;

/**
 * Представляет класс для просмотра тренировок пользователя.
 */
@Component
public class ViewTraining {

    private final TrainingHTTPMessenger trainingHTTPMessenger;

    @Autowired
    public ViewTraining(TrainingHTTPMessenger trainingHTTPMessenger) {
        this.trainingHTTPMessenger = trainingHTTPMessenger;
    }

    /**
     * Отображает все тренировки пользователя.
     *
     * @param userDTO Пользователь, чьи тренировки необходимо отобразить.
     */
    public void viewAllTraining(UserDTO userDTO) {
        try {
            System.out.println("userDTO id from user all training: " + userDTO.getId());
            TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining = trainingHTTPMessenger.getAllTrainings(userDTO);
            printAllTraining(allTraining);
        } catch (JsonProcessingException e) {
            System.out.println("Не удалось отобразить список тренировок пользователя (" + e.getMessage() + ")");
        }
    }

    private void printAllTraining(TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining) {
        if (allTraining.isEmpty()) {
            System.out.println("Список тренировок пуст");
            return;
        }

        for (Map.Entry<LocalDate, TreeSet<TrainingDTO>> entry : allTraining.entrySet()) {
            LocalDate currentDate = entry.getKey();
            TreeSet<TrainingDTO> trainingsOnDate = entry.getValue();

            System.out.println("\n" + "=====" + getStringFromDate(currentDate) + "=====");

            for (TrainingDTO training : trainingsOnDate) {
                System.out.println(training);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}
