package account.controller;

import account.dtos.UserDTO;
import account.exceptions.FieldValidationException;
import account.exceptions.UserExistException;
import account.model.User;
import account.service.UserService;
import account.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static final String hackerMessage = "The password is in the hacker's database!";
    private static final String diffMessage = "The passwords must be different!";
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity signUp(@Valid @RequestBody User user, BindingResult res) {
        if (res.hasErrors()) {
            List<FieldError> errors = res.getFieldErrors();
            for (FieldError error : errors) {
                // return only first error message
                throw new FieldValidationException(error.getDefaultMessage());
            }
        }
        User userResponse = userService.getUser(user.getEmail());
        if (userResponse != null) {
            throw new UserExistException();
        } else {
            if (PasswordUtils.isPasswordBreached(user.getPassword()))
                throw new FieldValidationException(hackerMessage);
            user.setPassword(encoder.encode(user.getPassword()));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UserDTO(userService.registerUser(user)));
        }

    }

    @PostMapping("/changepass")
    public ResponseEntity changePassword(@RequestBody Map<String, String> map,
                                         @AuthenticationPrincipal User user) {
        String newPassword = map.get("new_password");
        if (newPassword.length() < 12)
            throw new FieldValidationException("Password length must be 12 chars minimum!");
        if (PasswordUtils.isPasswordBreached(newPassword))
            throw new FieldValidationException(hackerMessage);
        else if (encoder.matches(newPassword, user.getPassword()))
            throw new FieldValidationException(diffMessage);
        else {
            user.setPassword(encoder.encode(newPassword));
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("email", user.getEmail(),
                            "status", "The password has been updated successfully"));
        }
    }
}
