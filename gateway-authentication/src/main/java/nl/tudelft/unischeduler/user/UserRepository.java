package nl.tudelft.unischeduler.user;

import nl.tudelft.unischeduler.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNetId(String netId);
}
