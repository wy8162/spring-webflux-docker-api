package y.w.api.springwebfluxdockerapi.ca;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import y.w.api.springwebfluxdockerapi.ca.model.CustomerAccount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccountResponse {
    List<CustomerAccount> accounts;

}
