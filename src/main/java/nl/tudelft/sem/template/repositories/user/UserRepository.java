package nl.tudelft.sem.template.repositories.user;

import nl.tudelft.sem.template.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<AppUser, String> {
    @Override
    List<AppUser> findAll();
}
