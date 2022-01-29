package y.w.api.springwebfluxdockerapi.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import y.w.api.springwebfluxdockerapi.model.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {
}
