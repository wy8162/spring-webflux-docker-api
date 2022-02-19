package y.w.api.springwebfluxdockerapi.hr.model;


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
@Table("regions")
public class Region {
    @Id
    @Column("region_id")
    private String regionId;

    @Column("region_name")
    private String regionName;
}
