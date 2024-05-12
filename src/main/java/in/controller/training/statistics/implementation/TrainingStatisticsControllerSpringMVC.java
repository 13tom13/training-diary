package in.controller.training.statistics.implementation;

import entity.dto.UserDTO;
import exceptions.security.rights.NoStatisticsRightsException;
import in.controller.training.statistics.TrainingStatisticsController;
import in.service.training.TrainingStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class TrainingStatisticsControllerSpringMVC implements TrainingStatisticsController {

    protected final TrainingStatisticsService trainingStatisticsService;

    @Override
    @GetMapping("/all")
    public ResponseEntity<?> getAllTrainingStatistics(@RequestParam("user") UserDTO userDTO) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatistics(userDTO);
            return ResponseEntity.ok(result);
        } catch (NoStatisticsRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @GetMapping("/period")
    public ResponseEntity<?> getAllTrainingStatisticsPerPeriod(@RequestParam("user") UserDTO userDTO, @RequestParam("start")LocalDate startDate, @RequestParam("end")LocalDate endDate) {
        try {
            int result = trainingStatisticsService.getAllTrainingStatisticsPerPeriod(userDTO, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (NoStatisticsRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @GetMapping("/period/duration")
    public ResponseEntity<?> getDurationStatisticsPerPeriod(@RequestParam("user") UserDTO userDTO, @RequestParam("start")LocalDate startDate, @RequestParam("end")LocalDate endDate) {
        try {
            int result = trainingStatisticsService.getDurationStatisticsPerPeriod(userDTO, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (NoStatisticsRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    @GetMapping("/period/calories")
    public ResponseEntity<?> getCaloriesBurnedPerPeriod(@RequestParam("user") UserDTO userDTO, @RequestParam("start")LocalDate startDate, @RequestParam("end")LocalDate endDate) {
        try {
            int result = trainingStatisticsService.getCaloriesBurnedPerPeriod(userDTO, startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (NoStatisticsRightsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
