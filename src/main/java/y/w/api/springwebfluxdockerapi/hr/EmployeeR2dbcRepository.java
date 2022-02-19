package y.w.api.springwebfluxdockerapi.hr;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.hr.model.Employee;

@Repository
public interface EmployeeR2dbcRepository extends ReactiveCrudRepository<Employee, Integer> {

    @Query("SELECT * FROM employees WHERE first_name = :firstName AND last_name = :lastName")
    Mono<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
