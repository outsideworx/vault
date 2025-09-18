package application.service;

import application.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
final class UserService implements UserDetailsService {
    private final UsersRepository usersRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository
                .findById(username)
                .orElseThrow(() -> {
                    log.error("User not found: [{}]", username);
                    return new UsernameNotFoundException("User not found.");
                });
    }
}