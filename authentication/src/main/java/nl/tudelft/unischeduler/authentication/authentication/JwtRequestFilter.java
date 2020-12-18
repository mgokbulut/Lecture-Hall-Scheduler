package nl.tudelft.unischeduler.authentication.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    @Getter
    @Setter
    private MyUserDetailsService userDetailsService;

    @Autowired
    @Getter
    @Setter
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = "";
        String jwt = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt += authorizationHeader.substring(7);
            username += jwtUtil.extractUsername(jwt);
        }

        if (!username.equals("")
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        try {
            jwt += "";
        } catch (Exception e) {
            System.out.println("This is just unnecessary!!!!");
        }

        filterChain.doFilter(request, response);
    }
}
