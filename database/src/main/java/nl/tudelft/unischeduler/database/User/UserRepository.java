package nl.tudelft.unischeduler.database.User;

import java.util.List;
import java.util.Optional;
import nl.tudelft.unischeduler.database.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();
    //List<User> findAllByNetId(List<String> netIds);

    Optional<User> findByNetId(String netId);
}
