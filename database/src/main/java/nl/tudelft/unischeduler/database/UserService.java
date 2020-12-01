package nl.tudelft.unischeduler.database;

import nl.tudelft.unischeduler.database.entities.User;
import nl.tudelft.unischeduler.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
