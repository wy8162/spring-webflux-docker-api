package y.w.api.springwebfluxdockerapi.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Default constructor is needed for Jackson
public class ApiResponse {

    private String version = "v1.0.0";
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}
