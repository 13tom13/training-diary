package in.repository.user.implementation;

import entity.model.User;
import in.repository.user.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepositorySpringJPA extends JpaRepository<User, Long>, UserRepository {
    Optional<User> getUserByEmail(String email);


}
