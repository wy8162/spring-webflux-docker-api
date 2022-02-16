package y.w.api.springwebfluxdockerapi.model.bookstore;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Book {
    // UUID.randomUUID().toString()
    @Id
    private String id;
    private String name;
    private List<Person> authors;
    private List<Review> reviews;
}
