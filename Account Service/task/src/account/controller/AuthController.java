package account.controller;

import account.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth/")
public class AuthController {
    @PostMapping("signup")
    public ResponseEntity signUp(@Valid @RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
    }
}
