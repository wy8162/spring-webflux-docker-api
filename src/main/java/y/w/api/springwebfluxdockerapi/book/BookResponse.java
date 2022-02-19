package y.w.api.springwebfluxdockerapi.book;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import y.w.api.springwebfluxdockerapi.book.model.Book;

@Data
@AllArgsConstructor
public class BookResponse {
    private final List<Book> books;
}
