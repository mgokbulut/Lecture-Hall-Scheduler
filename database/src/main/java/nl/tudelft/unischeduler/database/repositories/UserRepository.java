package nl.tudelft.unischeduler.database.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.unischeduler.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNetId(String netId);

    List<User> findAll();
}
