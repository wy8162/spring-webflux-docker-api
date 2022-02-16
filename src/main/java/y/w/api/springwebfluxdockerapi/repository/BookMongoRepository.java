package y.w.api.springwebfluxdockerapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import y.w.api.springwebfluxdockerapi.model.bookstore.Book;

public interface BookMongoRepository extends ReactiveMongoRepository<Book, String> { // ReactiveCrudRepository<Book, String>
}
