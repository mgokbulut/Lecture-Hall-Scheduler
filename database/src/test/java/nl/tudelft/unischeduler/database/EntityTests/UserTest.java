package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.unischeduler.database.User.User;
import nl.tudelft.unischeduler.database.User.UserRepository;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAndRetrieve() {
        User user = new User("Test", "Test", true, new Date());
        userRepository.save(user);
        User test = userRepository.findByNetId(user.getNetId());
        assertEquals(user, test);
    }

    @Test
    public void equals() {
        User user = new User("Test", "Test", true, new Date());
        User test = new User("Test", "Test", true, new Date());
        assertEquals(user, test);
    }
}
