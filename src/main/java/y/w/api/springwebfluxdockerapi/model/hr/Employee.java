package y.w.api.springwebfluxdockerapi.model.hr;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("employees")
public class Employee {

    @Id
    @Column("employee_id")
    private int employeeId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("hire_date")
    private LocalDateTime hireDate;

    @Column("job_id")
    private String jobId;

    @Column("salary")
    private double salary;

    @Column("commission_pct")
    private double commissionPct;

    @Column("manager_id")
    private int managerId;

    @Column("department_id")
    private int departmentId;
}
