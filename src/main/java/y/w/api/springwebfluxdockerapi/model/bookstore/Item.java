package y.w.api.springwebfluxdockerapi.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Item {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;

}
