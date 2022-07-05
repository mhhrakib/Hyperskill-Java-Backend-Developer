package account.service;

import account.model.Payment;
import account.model.User;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
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

    public List<Payment> getPayment(String email) {
        return userRepository.findPaymentByEmail(email);
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
