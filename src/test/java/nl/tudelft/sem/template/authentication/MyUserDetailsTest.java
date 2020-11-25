package nl.tudelft.sem.template.authentication;

import nl.tudelft.sem.template.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
    public void getUsernameTest() {
        assertEquals("user@student.tudelft.nl", userDetails.getUsername());
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
