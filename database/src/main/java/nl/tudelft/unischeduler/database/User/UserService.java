package nl.tudelft.unischeduler.database.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private transient UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
