package nl.tudelft.unischeduler.authentication;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    /**
     * Creates a token for the user if the provided credentials are correct.
     *
     * @return the token, or null
     * @throws Exception if the provided username or password is incorrect
     */
    public String createAuthenticationToken(String id, String password) {
        // hash the password before putting it to the authenticationManager in the future
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                id, password));
        } catch (BadCredentialsException e) {
            return null;
        }

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(id);

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return jwt;
    }
}
