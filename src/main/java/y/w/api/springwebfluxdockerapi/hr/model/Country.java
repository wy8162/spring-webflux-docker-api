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
@Table("countries")
public class Country {
    @Id
    @Column("country_id")
    private String countryId;

    @Column("country_name")
    private String countryName;

    @Column("region_id")
    private int regionId;
}
