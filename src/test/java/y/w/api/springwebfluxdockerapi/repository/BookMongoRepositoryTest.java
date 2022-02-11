package y.w.api.springwebfluxdockerapi.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

/**
 * Test Mongodb repository.
 */

@ExtendWith(SpringExtension.class)
@DataMongoTest
class BookMongoRepositoryTest {
    @Autowired
    private BookMongoRepository bookRepository;

    @Test
    void testFindById() {
        var books = bookRepository.findById("6205c710ca19162903abbdd8");

        books.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getName()).isEqualTo("Spring in Action");
            })
            .verifyComplete();
    }
}