package nl.tudelft.sem.template.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    /** Creates a token for the user if the provided credentials are correct.
     *
     * @return the token
     * @throws Exception if the provided username or password is incorrect
     */
    @PostMapping("/authenticate/{id}/{password}")
    public String createAuthenticationToken(
            @PathVariable String id,
            @PathVariable String password) {
        // hash the password before putting it to the authenticationManager in the future
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    id, password));
        } catch (BadCredentialsException e) {

            return "Invalid credentials";
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(id);

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return jwt;
    }
}
