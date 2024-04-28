package repository;

import in.repository.trainingtype.TrainingTypeRepository;
import in.repository.trainingtype.implementation.TrainingTypeRepositoryJDBC;
import entities.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static testutil.TestUtil.createConnectionToTestDatabase;
import static testutil.TestUtil.getTestUserFromDatabase;
import static org.junit.jupiter.api.Assertions.*;

public class TrainingTypeRepositoryJDBCTest {

    private static Connection connection;
    private static TrainingTypeRepository repository;
    private static User user;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = createConnectionToTestDatabase();
        repository = new TrainingTypeRepositoryJDBC(connection);
        user = getTestUserFromDatabase(connection);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testGetTrainingTypes() {
        // Insert sample training types for the user
        repository.saveTrainingType(user, "Тренировка 1");
        repository.saveTrainingType(user, "Тренировка 2");

        // Get training types for the user
        List<String> trainingTypes = repository.getTrainingTypes(user);

        // Verify the retrieved training types
        assertTrue(trainingTypes.contains("Тренировка 1"));
        assertTrue(trainingTypes.contains("Тренировка 2"));
    }

    @Test
    void testSaveTrainingType() {
        // Save a new training type for the user
        repository.saveTrainingType(user, "Новая тренировка");

        // Get training types for the user
        List<String> trainingTypes = repository.getTrainingTypes(user);

        // Verify the saved training type
        assertEquals(List.of("Новая тренировка"), trainingTypes);
    }
}
