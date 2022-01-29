package y.w.api.springwebfluxdockerapi.pojo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import y.w.api.springwebfluxdockerapi.model.Book;

@Data
@AllArgsConstructor
public class BookResponse {
    private final List<Book> books;
}
