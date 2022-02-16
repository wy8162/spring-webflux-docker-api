package y.w.api.springwebfluxdockerapi.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Person {
    @Id
    private String id;
    private String firstName;
    private String lastName;
}
