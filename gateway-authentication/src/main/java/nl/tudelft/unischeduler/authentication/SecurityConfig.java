package nl.tudelft.unischeduler.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    @Getter
    @Setter
    MyUserDetailsService userDetailsService;

    @Autowired
    @Getter
    @Setter
    JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // !!! NOTE:
        // The paths you do not specify are not authenticated -> accessable
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("authentication/**")
            .permitAll()
            .antMatchers("/authentication/examplepath", "/system/report_corona",
                "/system/course_information",
                "/system/student_schedule")
            .hasAnyRole("STUDENT", "TEACHER", "ADMIN")
            .antMatchers("/system/add_course", "/system/teacher_schedule", "/system/create_lecture")
            .hasAnyRole("TEACHER", "ADMIN")
            .antMatchers("/system/add_user", "/schedule-edit/**",
                "/database/**", "/rules/**", "/schedule-generate/**", "/viewer-module/**",
                "/user/**", "/users")
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