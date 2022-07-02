package engine.controller;

import engine.model.User;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("api/register")
    public ResponseEntity register(@Valid @RequestBody User user) {
        try {
            if (userService.getUser(user.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with email: " + user.getUsername() + " already taken");
            } else {
                user.setPassword(encoder.encode(user.getPassword()));
                userService.registerUser(user);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User with email: " + user.getUsername() + " created.");
            }
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("api/users")
    public List<User> register() {
        return userService.getAllUsers();
    }
}
