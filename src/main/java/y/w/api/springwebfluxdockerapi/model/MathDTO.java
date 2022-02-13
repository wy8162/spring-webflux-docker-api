package y.w.api.springwebfluxdockerapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(Include.NON_NULL) // See application.yml for spring.jackson.default-property-inclusion=non_null
public class MathDTO {
    private Integer firstNumber;
    private Integer secondNumber;
    private String operator;
    private String answer;
}
