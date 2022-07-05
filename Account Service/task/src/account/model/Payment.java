package account.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "payment", uniqueConstraints = {@UniqueConstraint
        (name = "UniqueEmployeePeriod", columnNames = {"employee", "period"})})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email(regexp = ".+@acme.com", message = "must be a valid email address")
    @Column(name = "employee")
    private String employee;

    @Min(value = 0, message = "Salary must be non negative!")
    private long salary;

    @Pattern(regexp = "(0[1-9]|1[0-2])-\\d{4}")
    private String period;

    public Payment() {
    }

    public Payment(String userEmail, long salary, String period) {
        this.employee = userEmail;
        this.salary = salary;
        this.period = period;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String userEmail) {
        this.employee = userEmail;
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
