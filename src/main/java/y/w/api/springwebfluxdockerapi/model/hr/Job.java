package y.w.api.springwebfluxdockerapi.model.hr;

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
@Table("jobs")
public class Job {

    @Id
    @Column("job_id")
    private int jobId;

    @Column("job_title")
    private String jobTitle;

    @Column("min_salary")
    private double minSalary;

    @Column("max_salary")
    private double maxSalary;
}
