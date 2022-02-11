package y.w.api.springwebfluxdockerapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerAccount;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerAccount.AccountType;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerPermission;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerRelationship;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerRelationship.Relationship;

@Service
public class CustomerAccountService {
    public Flux<Relationship> getRelationshipByProfileId(String profileId) {
        return Optional.ofNullable(customerRelationship.get(profileId))
            .map(r -> Flux.fromIterable(r.getRelationships()))
            .orElse(Flux.empty());
    }

    public Flux<String> getPermissionsByProfileId(String profileId) {
        return Optional.ofNullable(customerPermissions.get(profileId))
            .map(r -> Flux.fromIterable(r.getAccountNumbers()))
            .orElse(Flux.empty());
    }

    public Flux<CustomerAccount> getAccountsByAccountNumbers(List<String> accountNumbers) {
        var listOfAccounts = accounts
            .stream()
            .filter( a -> accountNumbers.contains(a.getAccountNumber()))
            .collect(Collectors.toList());

        return Flux.fromIterable(listOfAccounts);
    }

    public Flux<CustomerRelationship> getAllCustomerRelationships() {
        return Flux.fromIterable(customerRelationship.values());
    }

    public Flux<CustomerPermission> getAllPermissions() {
        return Flux.fromIterable(customerPermissions.values());
    }

    public Flux<CustomerAccount> getAllCustomerAccounts() {
        return Flux.fromIterable(accounts);
    }


    private static final Map<String, CustomerRelationship> customerRelationship = new HashMap<>();
    private static final Map<String, CustomerPermission> customerPermissions = new HashMap<>();
    private static final List<CustomerAccount> accounts = new ArrayList<>();

    static {
        customerRelationship.put("1",
            CustomerRelationship.builder().profileId("1").relationships(
            Arrays.asList(
                Relationship.builder().customerId("901").accountNumber("CC001").build(),
                Relationship.builder().customerId("901").accountNumber("CHK001").build(),
                Relationship.builder().customerId("911").accountNumber("CC002").build(),
                Relationship.builder().customerId("911").accountNumber("SAV001").build()
            )
        ).build());

        customerPermissions.put("1", CustomerPermission.builder().profileId("1").accountNumbers(Arrays.asList("CC001", "CHK001", "SAV001")).build());
        customerPermissions.put("2", CustomerPermission.builder().profileId("2").accountNumbers(Arrays.asList("CHK002")).build());
        customerPermissions.put("3", CustomerPermission.builder().profileId("3").accountNumbers(Arrays.asList("CC003", "CHK003", "SAV003")).build());

        customerRelationship.put("2",
            CustomerRelationship.builder().profileId("2").relationships(
            Arrays.asList(
                Relationship.builder().customerId("902").accountNumber("CHK002").build()
            )
        ).build());

        customerRelationship.put("3",
            CustomerRelationship.builder().profileId("3").relationships(
                Arrays.asList(
                    Relationship.builder().customerId("903").accountNumber("CC003").build(),
                    Relationship.builder().customerId("903").accountNumber("CHK003").build(),
                    Relationship.builder().customerId("903").accountNumber("SAV003").build()
                )
            ).build());

        accounts.addAll(
            Arrays.asList(
                CustomerAccount.builder().accountType(AccountType.CREDIT_CARD).accountNumber("CC001").customerName("Jack").balance(BigDecimal.valueOf(1000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.CREDIT_CARD).accountNumber("CC002").customerName("John").balance(BigDecimal.valueOf(2000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.CREDIT_CARD).accountNumber("CC003").customerName("Mike").balance(BigDecimal.valueOf(3000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.CHECKING).accountNumber("CHK001").customerName("Jack").balance(BigDecimal.valueOf(1000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.CHECKING).accountNumber("CHK002").customerName("John").balance(BigDecimal.valueOf(2000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.CHECKING).accountNumber("CHK003").customerName("Mike").balance(BigDecimal.valueOf(3000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.SAVING).accountNumber("SAV001").customerName("Jack").balance(BigDecimal.valueOf(1000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.SAVING).accountNumber("SAV002").customerName("John").balance(BigDecimal.valueOf(2000.00)).build(),
                CustomerAccount.builder().accountType(AccountType.SAVING).accountNumber("SAV003").customerName("Mike").balance(BigDecimal.valueOf(3000.00)).build()
            )
        );
    }
}
