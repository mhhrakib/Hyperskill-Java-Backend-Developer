package account.service;

import account.model.Payment;
import account.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment toSave) {
        return paymentRepository.save(toSave);
    }

    public Optional<Payment> getPayment(String employee, String period) {
        return paymentRepository.findPaymentByEmployeeAndPeriod(employee, period);
    }

    public List<Payment> getPayments(String employee) {
        return paymentRepository.findPaymentByEmployee(employee);
    }

    public List<Payment> savePayments(List<Payment> payments) {
        List<Payment> toSave = payments.stream().map(e -> {
            Payment payment = new Payment(e.getEmployee(), e.getSalary(), e.getPeriod());
            return payment;
        }).collect(Collectors.toList());
        return paymentRepository.saveAll(toSave);
    }

}
