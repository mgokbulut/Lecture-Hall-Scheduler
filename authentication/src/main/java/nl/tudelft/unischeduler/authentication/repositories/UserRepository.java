package nl.tudelft.unischeduler.authentication.repositories;


import nl.tudelft.unischeduler.authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    List<User> findAll();
}
