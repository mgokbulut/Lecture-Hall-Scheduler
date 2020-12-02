package nl.tudelft.unischeduler.database.User;

import nl.tudelft.unischeduler.database.User.User;
import nl.tudelft.unischeduler.database.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    //not sure if should be transient but checkstyle complaints without it...
    private transient UserRepository userRepository;

    public User getUser(String id){
        return userRepository.findByNetId(id).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
