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
@Table("departments")
public class Department {
    @Id
    @Column("department_id")
    private int departmentId;

    @Column("department_name")
    private String departmentName;

    @Column("manager_id")
    private int managerId;

    @Column("location_id")
    private int locationId;
}
