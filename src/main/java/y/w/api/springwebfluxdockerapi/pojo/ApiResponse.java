package y.w.api.springwebfluxdockerapi.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor // Default constructor is needed for Jackson
public class ApiResponse {
    private HttpStatus status = HttpStatus.OK;
    private String version = "v1.0.0";
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }
    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
