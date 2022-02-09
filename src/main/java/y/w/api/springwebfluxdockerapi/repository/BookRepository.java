package y.w.api.springwebfluxdockerapi.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import y.w.api.springwebfluxdockerapi.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> { // ReactiveCrudRepository<Book, String>
}
