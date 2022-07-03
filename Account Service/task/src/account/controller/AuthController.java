package account.controller;

import account.dtos.UserDTO;
import account.exceptions.UserExistException;
import account.model.User;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth/")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("signup")
    public ResponseEntity signUp(@Valid @RequestBody User user) {
        User userResponse = userService.getUser(user.getEmail());
        if (userResponse != null) {
            throw new UserExistException();
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UserDTO(userService.registerUser(user)));
        }
    }
}
