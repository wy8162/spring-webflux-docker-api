package y.w.api.springwebfluxdockerapi.general;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import y.w.api.springwebfluxdockerapi.book.BookMongoRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleDataInitializer {
    private final BookMongoRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        log.info("Initializing the data ...");

        initializeSampleData(0);
    }

    /**
     * Learned from Josh Long.
     */
    public void initializeSampleData(int seq) {
//        String postfix = (seq == 0 ? "" : " " + seq);
//
//        Flux<Book> books = Flux.just(
//                "Spring in Action" + postfix,
//                "Spring Boot in Action" + postfix,
//                "Java 8 for Impatient" + postfix,
//                "Kotlin in Action" + postfix
//            )
//            .map(name -> new Book(null, name))
//            .flatMap(this.bookRepository::save);
//
//        if (seq == 0)
//            this.bookRepository
//                    .deleteAll()
//                        .thenMany(books)
//                            .thenMany(this.bookRepository.findAll())
//                                .subscribe(b -> log.info(b.toString()));
//        else
//            books.subscribe(b -> log.info(b.toString()));
    }
}
