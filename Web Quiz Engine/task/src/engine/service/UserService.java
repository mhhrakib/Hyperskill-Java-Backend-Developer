package engine.service;

import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User toSave) {
        return userRepository.save(toSave);
    }

    public User getUser(String email) {
        return userRepository.findTopByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findTopByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + username);
        }
        return user;
    }
}
