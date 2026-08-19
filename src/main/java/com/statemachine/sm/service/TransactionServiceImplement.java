package com.statemachine.sm.service;

import com.statemachine.sm.domain.Account;
import com.statemachine.sm.errors.ApplicationException;
import com.statemachine.sm.errors.Errors;
import com.statemachine.sm.repository.AccountRepository;
import com.statemachine.sm.service.transaction.AccountDto;
import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplement implements TransactionService<AccountDto> {

    private final AccountRepository accountRepository;
    private final Map<String, Transaction> transactionMap = new HashMap<>();
    @Override
    public AccountDto transaction(Long id, String transaction) {
       validateTransaction(transaction);
       Account account = retrieveAccount(id);
       checkIfTransactionPossibleForCurrentStatus(id,transaction);
       return null;
    }



    public boolean validateTransaction(String transaction){
        final boolean contains =  transactionMap.keySet().contains(transaction);
        if (contains){
            throw new ApplicationException(Errors.INVALID_TRANSACTION, Map.of("transaction", transaction));
        }
        return false;
    }

    @Override
    public List<String> getAllowedTransitions(Long id) {
        return retrieveAccount(id).getAccountStatus().getTransactions();
    }

    private Account retrieveAccount(Long id){
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new ApplicationException(Errors.ACCOUNT_NOT_FOUND,
                        Map.of("id", id)));
    }


    private void checkIfTransactionPossibleForCurrentStatus(Long accountId, String transaction){
        final Optional<String> transaction = getAllowedTransitions(accountId)
                .stream()
                .filter(trans -> trans.equalsIgnoreCase(transaction))
                .findFirst();
    }

}
