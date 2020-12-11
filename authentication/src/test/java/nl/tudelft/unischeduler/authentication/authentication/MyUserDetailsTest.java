package nl.tudelft.unischeduler.authentication.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.unischeduler.authentication.MyUserDetails;
import nl.tudelft.unischeduler.authentication.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MyUserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(MyUserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
