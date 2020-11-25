package nl.tudelft.oopp.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate/**", "/register")
                .permitAll()

                .antMatchers(
                        "/bikes", "/bikes/**", "/user/bikes/*",
                        "/events", "/events/**", "/user/events/*",
                        "/reservations", "/reservations/**", "/user/reservations/*",
                        "/restaurants/orders", "/restaurants/orders/**",
                        "/availableRooms", "/availableRooms/**",
                        "/user", "/timeframes")
                .hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                .antMatchers(
                        "/addHoliday", "/addHoliday/**",
                        "/users", "/users/**")
                .hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,
                        "/buildings", "/buildings/**",
                        "/holidays", "/holidays/**",
                        "/products", "/products/**",
                        "/restaurants", "/restaurants/**",
                        "/rooms", "/rooms/**", "/room", "/room/**",
                        "/roomUtilities", "/roomUtilities/**")
                .hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                .antMatchers(HttpMethod.POST,
                        "/buildings", "/buildings/**",
                        "/holidays", "/holidays/**",
                        "/products", "/products/**",
                        "/rooms", "/rooms/**",
                        "/roomUtilities", "/roomUtilities/**",
                        "/roomProperty", "/roomProperty/**",
                        "/restaurants")
                .hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.PUT,
                        "/buildings", "/buildings/**",
                        "/holidays", "/holidays/**",
                        "/products", "/products/**",
                        "/rooms", "/rooms/**",
                        "/roomUtilities", "/roomUtilities/**",
                        "/roomProperty", "/roomProperty/**",
                        "/restaurants", "/restaurants/*")
                .hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.DELETE,
                        "/buildings", "/buildings/**",
                        "/holidays", "/holidays/**",
                        "/products", "/products/**",
                        "/rooms", "/rooms/**",
                        "/roomUtilities", "/roomUtilities/**",
                        "/roomProperty", "/roomProperty/**",
                        "/restaurants", "/restaurants/*")
                .hasAnyRole("ADMIN")

                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }





    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}