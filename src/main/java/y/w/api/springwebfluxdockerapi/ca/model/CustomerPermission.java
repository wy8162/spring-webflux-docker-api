package y.w.api.springwebfluxdockerapi.ca.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPermission {
    private String profileId;
    private List<String> accountNumbers;
}
