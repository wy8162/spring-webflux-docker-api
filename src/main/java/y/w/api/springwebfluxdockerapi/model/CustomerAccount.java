package y.w.api.springwebfluxdockerapi.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccount {
    private String customerName;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;

    public enum AccountType {
        CREDIT_CARD,
        CHECKING,
        SAVING
    }
}
