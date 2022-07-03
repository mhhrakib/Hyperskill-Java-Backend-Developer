package account.service;

import account.model.User;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User toSave) {
        return userRepository.save(toSave);
    }

    public User getUser(String email) {
        return userRepository.findTopByEmail(email.toLowerCase());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        if (user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException(String.format("Username[%s] not found"));
        }
    }
}
