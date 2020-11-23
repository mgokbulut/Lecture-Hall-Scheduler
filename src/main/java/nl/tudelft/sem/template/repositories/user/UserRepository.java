package nl.tudelft.sem.template.repositories.user;

import nl.tudelft.sem.template.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    @Override
    List<AppUser> findAll();
}
