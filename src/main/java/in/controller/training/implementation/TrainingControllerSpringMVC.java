package in.controller.training.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import out.messenger.TrainingRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
public class TrainingControllerSpringMVC implements TrainingController {

    private final TrainingService trainingService;
    private final ObjectMapper objectMapper;


    @Override
    @PostMapping("/save")
    public ResponseEntity<?> saveTraining(@RequestBody TrainingRequest request) {
        try {
            UserDTO userDTO = request.getUserDTO();
            TrainingDTO trainingDTO = request.getTrainingDTO();
            TrainingDTO savedTraining = trainingService.saveTraining(userDTO, trainingDTO);
            return ResponseEntity.ok(savedTraining);
        } catch (NoWriteRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTraining(@RequestParam("email") String email, @RequestParam("date") String date, @RequestParam("name") String name) {
        try {
            trainingService.deleteTraining(email, date, name);
            return ResponseEntity.ok().build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoDeleteRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PostMapping("/addition/add")
    public ResponseEntity<?> addTrainingAdditional(@RequestParam("name") String additionalName, @RequestParam("value") String additionalValue,
                                                   @RequestBody TrainingRequest trainingRequest) {
        try {
            String decodedName = URLDecoder.decode(additionalName, StandardCharsets.UTF_8);
            String decodedValue = URLDecoder.decode(additionalValue, StandardCharsets.UTF_8);
            System.out.println("addTrainingAdditional controller: " + decodedName + " " + decodedValue);
            UserDTO userDTO = trainingRequest.getUserDTO();
            TrainingDTO trainingDTO = trainingRequest.getTrainingDTO();
            trainingDTO = trainingService.addTrainingAdditional(userDTO, trainingDTO, additionalName, additionalValue);
            return ResponseEntity.ok(trainingDTO);
        } catch (NoWriteRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/addition/remove")
    public ResponseEntity<?> removeTrainingAdditional(@RequestBody TrainingDTO trainingDTO, @RequestParam("email") String email, @RequestParam("name") String additionalName) {
        try {
            trainingService.removeTrainingAdditional(email, trainingDTO, additionalName);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PutMapping("/edit/name")
    public ResponseEntity<?> changeNameTraining(@RequestBody TrainingDTO trainingDTO, @RequestParam("email") String email, @RequestParam("name") String newName) {
        try {
            trainingDTO = trainingService.changeNameTraining(trainingDTO, email, newName);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PutMapping("/edit/date")
    public ResponseEntity<?> changeDateTraining(@RequestBody TrainingDTO trainingDTO, @RequestParam("email") String email, @RequestParam("date") LocalDate newDate) {
        try {
            trainingDTO = trainingService.changeDateTraining(trainingDTO, email, newDate);
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
    public ResponseEntity<?> changeDurationTraining(@RequestBody TrainingDTO trainingDTO, @RequestParam("email") String email, @RequestParam("duration") int newDuration) {
        try {
            trainingDTO = trainingService.changeDurationTraining(trainingDTO, email, newDuration);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @PutMapping("/edit/calories")
    public ResponseEntity<?> changeCaloriesTraining(@RequestBody TrainingDTO trainingDTO, @RequestParam("email") String email, @RequestParam("calories") int newCalories) {
        try {
            trainingDTO = trainingService.changeCaloriesTraining(trainingDTO, email, newCalories);
            return ResponseEntity.ok(trainingDTO);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NoEditRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<TreeMap<LocalDate, TreeSet<TrainingDTO>>> getAllTrainings(@RequestParam("email") String email) {
        TreeMap<LocalDate, TreeSet<TrainingDTO>> allTraining = trainingService.getAllTrainings(email);
        return ResponseEntity.ok(allTraining);
    }

    @Override
    @GetMapping("/get/byDate")
    public ResponseEntity<?> getTrainingsByUserEmailAndData(@RequestParam("email") String email, @RequestParam("date") String trainingDate) {
        try {
            TreeSet<TrainingDTO> allTraining = trainingService.getTrainingsByUserEmailAndData(email, trainingDate);
            return ResponseEntity.ok(allTraining);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<?> getTrainingByUserEmailAndDateAndName(@RequestParam("email") String email, @RequestParam("date") String trainingDate, @RequestParam("name") String trainingName) {
        try {
            TrainingDTO training = trainingService.getTrainingByUserEmailAndDataAndName(email, trainingDate, trainingName);
            return ResponseEntity.ok(training);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/type/get")
    public ResponseEntity<?> getTrainingTypes(@RequestParam("id") long id) {
        List<String> trainingTypes = trainingService.getTrainingTypes(id);
        return ResponseEntity.ok(trainingTypes);
    }

    @Override
    @PostMapping("/type/save")
    public ResponseEntity<Void> saveTrainingType(@RequestParam("id") long id, @RequestBody String customTrainingType) {
        trainingService.saveTrainingType(id, customTrainingType);
        return ResponseEntity.ok().build();
    }
}
