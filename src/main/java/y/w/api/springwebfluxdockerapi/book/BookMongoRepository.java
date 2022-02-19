package y.w.api.springwebfluxdockerapi.book;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import y.w.api.springwebfluxdockerapi.book.model.Book;

@Repository
public interface BookMongoRepository extends ReactiveMongoRepository<Book, String> { // ReactiveCrudRepository<Book, String>
}
