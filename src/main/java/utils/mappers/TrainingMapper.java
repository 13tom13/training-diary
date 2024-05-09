package utils.mappers;

import entity.dto.TrainingDTO;
import entity.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;

@Mapper
public interface TrainingMapper {

    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "date", source = "date"),
            @Mapping(target = "duration", source = "duration"),
            @Mapping(target = "caloriesBurned", source = "caloriesBurned"),
            @Mapping(target = "additions", source = "additions", qualifiedByName = "additionsToMap")
    })
    TrainingDTO trainingToTrainingDTO(Training training);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "date", source = "date"),
            @Mapping(target = "duration", source = "duration"),
            @Mapping(target = "caloriesBurned", source = "caloriesBurned"),
            @Mapping(target = "additions", source = "additions", qualifiedByName = "additionsToMap")
    })
    Training trainingDTOToTraining(TrainingDTO trainingDTO);

    @Named("additionsToMap")
    default HashMap<String, String> additionsToMap(HashMap<String, String> additions) {
        if (additions != null) {
            return new HashMap<>(additions);
        } else {
            return new HashMap<>();
        }
    }
}
