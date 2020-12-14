package nl.tudelft.unischeduler.database.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.unischeduler.database.user.User;
import nl.tudelft.unischeduler.database.user.UserRepository;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {
    @Autowired
    private transient UserRepository userRepository;

    @Test
    public void saveAndRetrieve() {
        User user = new User("Test", "Test", true, new Date());
        userRepository.save(user);
        User test = userRepository.findByNetId(user.getNetId()).get();
        assertEquals(user, test);
    }

    @Test
    public void equals() {
        User user = new User("Test", "Test", true, new Date());
        User test = new User("Test", "Test", true, new Date());
        assertEquals(user, test);
    }
}
