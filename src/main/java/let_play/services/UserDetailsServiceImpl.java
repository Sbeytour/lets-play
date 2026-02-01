package let_play.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import let_play.entities.User;
import let_play.repositories.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(identifier)
                    .orElseThrow(() -> null); // handle exception

            return user;
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found by identifier: " + identifier);
        }
    }
}
