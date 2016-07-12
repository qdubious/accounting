package core;

import com.google.common.base.MoreObjects;
import core.account.Account;
import core.account.AccountDetails;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class describes a ledger's accounts with their corresponding balances at a specific moment in time.
 */
public class TrialBalanceResult {
    final private Map<AccountDetails, BigDecimal> accountDetailsToBalance =
            new TreeMap<>((o1, o2) -> o1.getAccountNumber().compareTo(o2.getAccountNumber()));
    final private long creationTimestamp;
    final private boolean isBalanced;

    public TrialBalanceResult(Set<Account> accounts) {
        if (accounts == null || accounts.isEmpty())
            throw new IllegalArgumentException();
        accounts.forEach(
                a -> accountDetailsToBalance.put(a.getAccountDetails(), a.getBalance())
        );
        creationTimestamp = Instant.now().toEpochMilli();
        BigDecimal balance = BigDecimal.ZERO;
        accounts.forEach(a -> balance.add(a.getBalance()));
        isBalanced = balance == BigDecimal.ZERO ? true : false;
    }

    public Map<AccountDetails, BigDecimal> getAccountDetailsToBalance() {
        return new HashMap<>(accountDetailsToBalance);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("isBalanced", isBalanced)
                .add("accountDetailsToBalance", accountDetailsToBalance)
                .add("creationTimestamp", Instant.ofEpochMilli(creationTimestamp).toString())
                .toString();
    }
}