package account.controller;

import account.dtos.UserDTO;
import account.model.User;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/empl/")
public class EmployeeController {
    @Autowired
    UserService userService;

    @GetMapping("payment")
    public ResponseEntity testPayment(@AuthenticationPrincipal User user) {
        User userResponse = userService.getUser(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userResponse));
    }
}
