package y.w.api.springwebfluxdockerapi.fruit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Fruit {
    @Id
    private String id;
    private String name;
    private int quantity;
}
