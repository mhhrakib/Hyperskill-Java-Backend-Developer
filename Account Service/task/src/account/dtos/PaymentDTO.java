package account.dtos;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaymentDTO {
    @NotBlank
    @Email(regexp = ".+@acme.com", message = "must be a valid email address")
    private String employee;

    @Min(value = 0, message = "Salary must be non negative!")
    private long salary;

    @Column(unique = true)
    private String period;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
