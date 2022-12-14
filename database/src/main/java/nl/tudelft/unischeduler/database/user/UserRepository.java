package nl.tudelft.unischeduler.database.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();

    Optional<User> findByNetId(String netId);
}
