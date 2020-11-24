package nl.tudelft.sem.template.repositories.user;

import nl.tudelft.sem.template.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Override
    List<User> findAll();
}
