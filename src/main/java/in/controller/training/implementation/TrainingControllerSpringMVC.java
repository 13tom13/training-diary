package in.controller.training.implementation;

import entity.dto.TrainingDTO;
import entity.dto.UserDTO;
import exceptions.InvalidDateFormatException;
import exceptions.RepositoryException;
import exceptions.security.rights.NoDeleteRightsException;
import exceptions.security.rights.NoEditRightsException;
import exceptions.security.rights.NoWriteRightsException;
import in.controller.training.TrainingController;
import in.service.training.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
public class TrainingControllerSpringMVC implements TrainingController {

    private final TrainingService trainingService;


    @Override
    @PostMapping("/save")
    public ResponseEntity<?> saveTraining(@RequestBody UserDTO userDTO, TrainingDTO trainingDTO) {
        try {
            trainingDTO = trainingService.saveTraining(userDTO, trainingDTO);
            return ResponseEntity.ok(trainingDTO);
        } catch (NoWriteRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTraining(@RequestParam("user") UserDTO userDTO, @RequestParam("date")String date, @RequestParam("name")String name) {
            try {
                trainingService.deleteTraining(userDTO, date, name);
                return ResponseEntity.ok().build();
            } catch (RepositoryException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            } catch (NoDeleteRightsException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
    }

    @Override
    @PostMapping("/addTrainingAdditional")
    public ResponseEntity<?> addTrainingAdditional(@RequestBody UserDTO userDTO, TrainingDTO trainingDTO, String additionalName, String additionalValue) {
            try {
                trainingDTO = trainingService.addTrainingAdditional(userDTO, trainingDTO, additionalName, additionalValue);
            return ResponseEntity.ok(trainingDTO);
            } catch (NoWriteRightsException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } catch (RepositoryException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }

    @Override
    @DeleteMapping("/removeTrainingAdditional")
    public ResponseEntity<?> removeTrainingAdditional(@RequestParam("user") UserDTO userDTO,@RequestParam("training") TrainingDTO trainingDTO,@RequestParam("name") String additionalName) {
        try {
            trainingService.removeTrainingAdditional(userDTO, trainingDTO, additionalName);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PutMapping("/edit/name")
    public ResponseEntity<?> changeNameTraining(@RequestBody UserDTO userDTO, TrainingDTO trainingDTO, String newName) {
        try {
            trainingDTO = trainingService.changeNameTraining(userDTO, trainingDTO, newName);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PutMapping("/edit/date")
    public ResponseEntity<?> changeDateTraining(@RequestBody UserDTO userDTO, TrainingDTO trainingDTO, LocalDate newDate) {
        try {
            trainingDTO = trainingService.changeDateTraining(userDTO, trainingDTO, newDate);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (InvalidDateFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    @PutMapping("/edit/duration")
    public ResponseEntity<?> changeDurationTraining(@RequestBody UserDTO userDTO, TrainingDTO trainingDTO, String newDuration) {
        try {
            trainingDTO = trainingService.changeNameTraining(userDTO, trainingDTO, newDuration);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PutMapping("/edit/calories")
    public ResponseEntity<?> changeCaloriesTraining(@RequestBody UserDTO userDTO, TrainingDTO trainingDTO, String newCalories) {
        try {
            trainingDTO = trainingService.changeNameTraining(userDTO, trainingDTO, newCalories);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @GetMapping("/AllTrainings")
    public ResponseEntity<TreeMap<LocalDate, TreeSet<TrainingDTO>>> getAllTrainings(@RequestParam("user") UserDTO userDTO) {
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining = trainingService.getAllTrainings(userDTO);
        return ResponseEntity.ok(allTraining);
    }

    @Override
    @GetMapping("/trainingByDate")
    public ResponseEntity<?> getTrainingsByUserEmailAndData(@RequestParam("user") UserDTO userDTO,@RequestParam("date") String trainingDate) {
        try {
            TreeSet<TrainingDTO> allTraining = trainingService.getTrainingsByUserEmailAndData(userDTO, trainingDate);
            return ResponseEntity.ok(allTraining);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getTrainingByUserEmailAndDateAndName(@RequestParam("user") UserDTO userDTO,@RequestParam("date") String trainingDate,@RequestParam("name") String trainingName) {
        try {
            TrainingDTO training = trainingService.getTrainingByUserEmailAndDataAndName(userDTO, trainingDate, trainingName);
            return ResponseEntity.ok().build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getTrainingTypes(@RequestParam("user") UserDTO userDTO) {
        List<String> trainingTypes = trainingService.getTrainingTypes(userDTO);
        return ResponseEntity.ok(trainingTypes);
    }

    @Override
    public ResponseEntity<Void> saveTrainingType(@RequestParam("user") UserDTO userDTO, @RequestParam("type") String customTrainingType) {
        trainingService.saveTrainingType(userDTO, customTrainingType);
        return ResponseEntity.ok().build();
    }
}
