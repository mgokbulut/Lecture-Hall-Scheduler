package nl.tudelft.sem.template.repositories.user;

import nl.tudelft.sem.template.entities.AppUser;
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
    public Iterable<AppUser> getAllUsers() {
        System.out.println("kinda works !!!!");
        return userRepository.findAll();
        //Iterable returns
        //System.out.println(userRepository.findAll().toString());
        //return this.list; // fetch the users from the database
    }
}
