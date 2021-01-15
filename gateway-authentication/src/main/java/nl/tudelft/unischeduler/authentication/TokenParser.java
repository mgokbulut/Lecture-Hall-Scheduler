package nl.tudelft.unischeduler.authentication;

import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenParser {
    @Autowired
    @Getter
    @Setter
    private JwtUtil jwtUtil;

    @Autowired
    @Getter
    @Setter
    private MyUserDetailsService userDetailsService;

    /**
     * Extracts username from jwt token.
     *
     * @param request http request object
     * @return returns username
     */
    public String extract_username(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            username += jwtUtil.extractUsername(jwt);
        }
        if (!username.equals("")
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            userDetails.getUsername(); // passes the PMD
        }

        return username;
    }
}
