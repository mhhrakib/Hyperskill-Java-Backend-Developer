package account.controller;

import account.exceptions.FieldValidationException;
import account.model.Payment;
import account.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/acct")
public class AccountController {
    @Autowired
    private PaymentService paymentService;

    private void handleFieldValidation(BindingResult res) {
        if (res.hasErrors()) {
            List<FieldError> errors = res.getFieldErrors();
            for (FieldError error : errors) {
                // return only first error message
                throw new FieldValidationException(error.getDefaultMessage());
            }
        }
    }

    @PostMapping("/payments")
    public ResponseEntity addPayment(@RequestBody List<@Valid Payment> payments, BindingResult res) {
        try {
            handleFieldValidation(res);
        } catch (Exception exception) {
            throw new FieldValidationException(exception.getMessage());
        }
        try {
            paymentService.savePayments(payments);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "Added successfully!"));
        } catch (Exception exception) {
            //Deceiving exception handler
            throw new FieldValidationException(exception.getMessage());
        }
    }

    @PutMapping("/payments")
    public ResponseEntity updatePayment(@RequestBody @Valid Payment payment, BindingResult res) {
        try {
            handleFieldValidation(res);
        } catch (Exception exception) {
            throw new FieldValidationException(exception.getMessage());
        }
        try {
            Optional<Payment> tmp = paymentService.getPayment(payment.getEmployee(), payment.getPeriod());
            if(tmp.isPresent()) {
                Payment toUpdate = tmp.get();
                toUpdate.setSalary(payment.getSalary());
                paymentService.save(toUpdate);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "Updated successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
            }
        } catch (Exception exception) {
            //Deceiving exception handler
            throw new FieldValidationException(exception.getMessage());
        }
    }
}
