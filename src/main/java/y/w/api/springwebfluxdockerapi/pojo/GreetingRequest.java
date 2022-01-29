package y.w.api.springwebfluxdockerapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GreetingRequest {
    private final String name;
}
