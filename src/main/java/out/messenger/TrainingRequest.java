package out.messenger;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;

public class TrainingRequest {

    private UserDTO userDTO;
    private TrainingDTO trainingDTO;


    public TrainingRequest() {
    }

    public TrainingRequest(UserDTO userDTO, TrainingDTO trainingDTO) {
        this.userDTO = userDTO;
        this.trainingDTO = trainingDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public TrainingDTO getTrainingDTO() {
        return trainingDTO;
    }

    public void setTrainingDTO(TrainingDTO trainingDTO) {
        this.trainingDTO = trainingDTO;
    }
}
