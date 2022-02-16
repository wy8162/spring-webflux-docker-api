package y.w.api.springwebfluxdockerapi.model.bookstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class CartItem {
    private Item item;
    private int quantity;
}
