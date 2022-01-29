package y.w.api.springwebfluxdockerapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import y.w.api.springwebfluxdockerapi.model.Book;
import y.w.api.springwebfluxdockerapi.repository.BookRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleDataInitializer {
    private final BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        log.info("Initializing the data ...");

        initializeSampleData();
    }

    /**
     * Learned from Josh Long.
     */
    public void initializeSampleData() {
        Flux<Book> books = Flux.just(
                "Spring in Action",
                "Spring Boot in Action",
                "Java 8 for Impatient",
                "Kotlin in Action"
            )
            .map(name -> new Book(null, name))
            .flatMap(this.bookRepository::save);

        this.bookRepository
                .deleteAll()
                    .thenMany(books)
                        .thenMany(this.bookRepository.findAll())
                            .subscribe(b -> log.info(b.toString()));
    }
}
