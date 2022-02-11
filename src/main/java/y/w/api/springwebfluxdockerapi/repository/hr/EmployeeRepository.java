package y.w.api.springwebfluxdockerapi.repository.hr;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.model.hr.Employee;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Integer> {

    @Query("SELECT * FROM employees WHERE first_name = :firstName AND last_name = :lastName")
    Mono<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
