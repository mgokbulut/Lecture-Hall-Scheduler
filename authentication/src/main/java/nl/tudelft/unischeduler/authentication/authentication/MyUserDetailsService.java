package nl.tudelft.unischeduler.authentication.authentication;

import java.util.Optional;
import lombok.Data;
import nl.tudelft.unischeduler.authentication.user.User;
import nl.tudelft.unischeduler.authentication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
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