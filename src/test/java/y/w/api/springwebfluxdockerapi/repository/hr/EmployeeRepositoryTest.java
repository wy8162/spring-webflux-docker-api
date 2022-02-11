package y.w.api.springwebfluxdockerapi.repository.hr;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import y.w.api.springwebfluxdockerapi.model.hr.Employee;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
class EmployeeRepositoryTest {
    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByEmployeeId() {
        Mono<Employee> employeeMono = employeeRepository
            .findById(100);

        employeeMono.as(StepVerifier::create)
            .assertNext(actual -> {
                assertThat(actual.getFirstName()).isEqualTo("Steven");
                assertThat(actual.getLastName()).isEqualTo("King");
            })
            .verifyComplete();
    }
}