package nl.tudelft.sem.template.authentication;

import java.util.Optional;
import nl.tudelft.sem.template.user.User;
import nl.tudelft.sem.template.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String netId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByNetId(netId);

        user.orElseThrow(() -> new UsernameNotFoundException("User with NetID "
            + netId + " not found"));

        return user.map(MyUserDetails::new).get();
    }
}