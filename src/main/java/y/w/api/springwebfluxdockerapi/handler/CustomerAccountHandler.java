package y.w.api.springwebfluxdockerapi.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import y.w.api.springwebfluxdockerapi.model.CustomerAccount;
import y.w.api.springwebfluxdockerapi.model.CustomerPermission;
import y.w.api.springwebfluxdockerapi.model.CustomerRelationship;
import y.w.api.springwebfluxdockerapi.pojo.CustomerAccountResponse;
import y.w.api.springwebfluxdockerapi.pojo.ErrorMessage;
import y.w.api.springwebfluxdockerapi.service.CustomerAccountService;

@RequiredArgsConstructor
@Component
public class CustomerAccountHandler {
    private final CustomerAccountService customerAccountService;

    public Mono<ServerResponse> retrieveAccountsByProfileId(ServerRequest request) {
        String profileId = request.pathVariable("profileId");

        if (profileId == null) return ServerResponse.status(HttpStatus.BAD_REQUEST).body(BodyInserters.fromValue(new ErrorMessage("BAD REQUEST")));

        var customerAccountResponse = customerAccountService.getRelationshipByProfileId(profileId)
            .map(r -> r.getAccountNumber())
            .collectList()
            .flatMap(l -> customerAccountService.getAccountsByAccountNumbers(l).collectList().map(list -> new CustomerAccountResponse(list)));


        return ServerResponse.ok().body(BodyInserters.fromPublisher(customerAccountResponse, CustomerAccountResponse.class));
    }

    public Mono<ServerResponse> getAllCustomerRelationships(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(BodyInserters.fromPublisher(customerAccountService.getAllCustomerRelationships(), CustomerRelationship.class));
    }

    public Mono<ServerResponse> getAllPermissions(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(BodyInserters.fromPublisher(customerAccountService.getAllPermissions(), CustomerPermission.class));
    }

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return ServerResponse
            .ok()
            .body(BodyInserters.fromPublisher(customerAccountService.getAllCustomerAccounts(), CustomerAccount.class));
    }
}
