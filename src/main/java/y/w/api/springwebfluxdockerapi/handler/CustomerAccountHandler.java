package y.w.api.springwebfluxdockerapi.handler;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerAccount;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerPermission;
import y.w.api.springwebfluxdockerapi.model.ca.CustomerRelationship;
import y.w.api.springwebfluxdockerapi.pojo.CustomerAccountResponse;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;
import y.w.api.springwebfluxdockerapi.service.CustomerAccountService;

@RequiredArgsConstructor
@Component
public class CustomerAccountHandler {

    private final CustomerAccountService customerAccountService;

    public Mono<ServerResponse> retrieveAccountsByProfileId(ServerRequest request) {
        String profileId = request.pathVariable("profileId");

        if (profileId == null) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .bodyValue(new ErrorMessage("BAD REQUEST"));
        }

        var customerAccountResponse = customerAccountService.getRelationshipByProfileId(profileId)
            .map(r -> r.getAccountNumber())
            .collect(Collectors.toSet())
            // Permissions determine those serviceable accounts.
            .zipWith(customerAccountService.getPermissionsByProfileId(profileId)
                .collect(Collectors.toSet()), (s1, s2) -> {
                s1.retainAll(s2);
                return s1;
            })
            .flatMap(
                s -> customerAccountService.getAccountsByAccountNumbers(new ArrayList<>(s))
                    .collectList().map(list -> new CustomerAccountResponse(list)));

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
            BodyInserters.fromPublisher(customerAccountResponse, CustomerAccountResponse.class));
    }

    public Mono<ServerResponse> getAllCustomerRelationships(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromPublisher(customerAccountService.getAllCustomerRelationships(),
                CustomerRelationship.class));
    }

    public Mono<ServerResponse> getAllPermissions(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(customerAccountService.getAllPermissions(), CustomerPermission.class);
    }

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(customerAccountService.getAllCustomerAccounts(), CustomerAccount.class);
            //.body(BodyInserters.fromPublisher(customerAccountService.getAllCustomerAccounts(),CustomerAccount.class));
    }
}
