package nl.tudelft.unischeduler.authentication.repositories;

import nl.tudelft.unischeduler.authentication.entities.User;
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
