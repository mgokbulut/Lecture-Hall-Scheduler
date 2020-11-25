package nl.tudelft.sem.template.user;

import nl.tudelft.sem.template.user.User;
import nl.tudelft.sem.template.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Register user method.
     * @param usr input username
     * @return result of operation
     */
    public String register(User usr) {
        //check database values with the supplied values
        //return true if netID and password matches an entry

        //for now
        System.out.println("------------------------------------------------");
        Optional<User> tempUser = userRepository.findById(usr.getNetID());
        if (!tempUser.isEmpty()) {
            //prints the user in the database
            //User user = tempUser.get();
            //System.out.println(user.toString());
            System.out.println("This NetID already exists");
            return "This NetID already exists";

        } else {
            usr.setPassword("" + usr.getPassword().hashCode());
            userRepository.save(usr);
            System.out.println("successfully added");
            return "successfully added";
        }
        //System.out.println(usr.toString());
    }

    /**
     * Get all users method.
     * @return returns all users from database
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
        //Iterable returns
        //System.out.println(userRepository.findAll().toString());
        //return this.list; // fetch the users from the database
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
