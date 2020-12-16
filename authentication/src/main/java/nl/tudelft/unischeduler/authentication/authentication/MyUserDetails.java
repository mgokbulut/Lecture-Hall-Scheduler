package nl.tudelft.unischeduler.authentication.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import nl.tudelft.unischeduler.authentication.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class MyUserDetails implements UserDetails {

    static final long serialVersionUID = 42L;
    private String netid;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;
    private String role;

    public MyUserDetails() {

    }

    /**
     * Constructor for MyUserDetails.
     *
     * @param user - the user to get the details from - also sets authority to their role
     */
    public MyUserDetails(User user) {
        this.netid = user.getNetId();
        this.password = user.getPassword();
        this.role = user.authenticationRole();
        this.authorities = Arrays.stream(user.authenticationRole().split(" "))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return netid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isActive() {
        return active;
    }
}
