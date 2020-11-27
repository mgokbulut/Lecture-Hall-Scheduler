package nl.tudelft.sem59.authentication.repositories;

import nl.tudelft.sem59.authentication.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users method.
     * @return returns all users from database
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
