package account.controller;

import account.exceptions.FieldValidationException;
import account.model.Payment;
import account.model.User;
import account.service.PaymentService;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/empl/")
public class EmployeeController {
    @Autowired
    UserService userService;
    @Autowired
    PaymentService paymentService;

    private Map<String, String> buildResponse(String name, String lastname, Payment payment) {
        Map <String, String> res = new HashMap<>();
        res.put("name", name);
        res.put("lastname", lastname);
        res.put("salary", payment.getSalary() / 100 + " dollar(s) " + payment.getSalary() % 100 + " cent(s)");
        LocalDate date = LocalDate.parse("01-" +payment.getPeriod(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        res.put("period", date.format(DateTimeFormatter.ofPattern("MMMM-yyyy")));
        return res;
    }


    @GetMapping("payment")
    public ResponseEntity getPayment(@RequestParam Optional<String> period, @AuthenticationPrincipal User user) {
        if(period.isPresent()) {
            if(period.get().matches("(0[1-9]|1[0-2])-\\d{4}")) {
                Optional<Payment> payment = paymentService.getPayment(user.getEmail(), period.get());
                if(payment.isPresent()) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(buildResponse(user.getName(), user.getLastname(), payment.get()));
                } else {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new ArrayList<>());
                }
            } else {
                throw new FieldValidationException("Period is not properly formatted.");
            }
        } else {
            List<Map<String, String>> result = paymentService.getPayments(user.getEmail())
                    .stream().map(e -> {
                        Map<String, String> map= buildResponse(user.getName(), user.getLastname(), e);
                        return map;
                    }).collect(Collectors.toList());
            Comparator<Map<String,String>> sortByDate = Comparator
                    .comparing(x -> LocalDate.parse("01-" +x.get("period"), DateTimeFormatter.ofPattern("dd-MMMM-yyyy")));
            Collections.sort(result, sortByDate.reversed());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }
}
