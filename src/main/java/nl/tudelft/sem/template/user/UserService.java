package nl.tudelft.sem.template.user;

import nl.tudelft.sem.template.authentication.AuthenticationService;
import nl.tudelft.sem.template.user.User;
import nl.tudelft.sem.template.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Register user method.
     * @param user input username
     * @return result of operation
     */
    public String register(User user) {
        Optional<User> tmp = userRepository.findById(user.getNetID());
        if (!tmp.isEmpty()) {
            return "{message:\"This NetID already exists\"}";
        } else {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(user.getPassword().getBytes());
                String stringHash = new String(messageDigest.digest());
                user.setPassword(stringHash);
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println("Something went wrong in register method");
                return null;
            }
            return "{message:\"Successfully registered\"}";
        }
    }

    /**
     * Login user method
     * @param user
     * @return returns error message or authentication token
     */
    public String login(User user) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(user.getPassword().getBytes());
            String stringHash = new String(messageDigest.digest());
            user.setPassword(stringHash);
            String token = authenticationService.createAuthenticationToken(user.getNetID(), user.getPassword());
            if(token == null) {
                return "{message:\"Invalid credentials\"}";
            }
            return "{token:\"" + token + "\"}";
        } catch (Exception e) {
            System.out.println("There was a problem in login route in User Service");
        }
        return null;
    }
    /**
     * Get all users method.
     * @return returns all users from database
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user information by netID.
     * @param netID - netID
     * @return
     */
    public Optional<User> getUser(String netID) {
        Optional<User> optionalUser = userRepository.findById(netID);
        User user = optionalUser.get();
        user.setPassword("");
        return Optional.of(user);
    }
}

