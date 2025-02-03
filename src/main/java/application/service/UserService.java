package application.service;

import application.entity.security.User;
import application.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public final class UserService implements UserDetailsService {
    private final UsersRepository usersRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found."));
    }
    
    public void save(User user) {
        if (usersRepository.existsById(user.getUsername())) {
            throw new AuthenticationServiceException("Member already exists.");
        }
        usersRepository.save(user);
    }
}