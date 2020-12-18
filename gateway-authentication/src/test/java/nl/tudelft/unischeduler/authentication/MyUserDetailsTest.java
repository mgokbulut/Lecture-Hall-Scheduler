package nl.tudelft.unischeduler.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import nl.tudelft.unischeduler.user.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@SpringBootTest
public class MyUserDetailsTest {

    User user = new User("user@student.tudelft.nl", "pass", "STUDENT");
    MyUserDetails userDetails = new MyUserDetails(user);

    @Test
    public void emptyConstructorTest() {
        MyUserDetails myUserDetails2 = new MyUserDetails();
        assertNotNull(myUserDetails2);
    }

    @Test
    public void getNetIdTest() {
        assertEquals("user@student.tudelft.nl", userDetails.getNetid());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("pass", userDetails.getPassword());
    }

    @Test
    public void getAuthoritiesTest() {
        String rolee = "ROLE_STUDENT";
        List<GrantedAuthority> authorities = Arrays.stream(rolee.split(" "))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test
    public void isAccountNonExpiredTest() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLockedTest() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpiredTest() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void isEnabledTest() {
        assertTrue(userDetails.isEnabled());
    }
}
