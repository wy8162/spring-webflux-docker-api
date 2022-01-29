package y.w.api.springwebfluxdockerapi.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Default constructor is needed for Jackson
public class GreetingResponse {

    private String version = "v1.0.0";
    private String message;

    public GreetingResponse(String message) {
        this.message = message;
    }
}
