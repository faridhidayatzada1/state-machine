package com.statemachine.sm.service;

import com.statemachine.sm.config.ModelMapperConfig;
import com.statemachine.sm.domain.Account;
import com.statemachine.sm.errors.ApplicationException;
import com.statemachine.sm.errors.Errors;
import com.statemachine.sm.repository.AccountRepository;
import com.statemachine.sm.service.transaction.AccountDto;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImplement implements TransactionService<AccountDto> {

    private final AccountRepository accountRepository;
    private final ModelMapperConfig modelMapper;
    private final List<Transition<AccountDto>> transitions; // ← Spring inject edir

    // transactionMap artıq field deyil
    private Map<String, Transition<AccountDto>> transactionMap;

    @PostConstruct
    public void init() {
        this.transactionMap = transitions.stream()
                .collect(Collectors.toMap(Transition::getName, t -> t));
    }

    @Override
    @Transactional
    public AccountDto transaction(Long id, String transStr) {
       validateTransaction(transStr);
       Account account = retrieveAccount(id);
        System.out.println("account " + account);
       final Transition<AccountDto> transaction = getTransaction(id, transStr);
        System.out.println("transaction " + transaction);
       transaction.applyProcessing(modelMapper.modelMapper().map(account, AccountDto.class));
       final Account updatedAccount = updateStatus(account, transaction, transaction.getTargetStatus());
       return modelMapper.modelMapper().map(updatedAccount, AccountDto.class);
    }



    public boolean validateTransaction(String transaction){

        final boolean contains =  transactionMap.keySet().contains(transaction);
        if (!contains){
            throw new ApplicationException(Errors.INVALID_ACCOUNT_STATUS, Map.of("transaction", transaction));
        }
        return false;
    }

    @Override
    public List<String> getAllowedTransitions(Long id) {
        return retrieveAccount(id).getAccountStatus().getTransactions();
    }

    private Transition<AccountDto> getTransaction(Long accountId, String transaction){
        final Optional<String> optionalTransaction = getAllowedTransitions(accountId)
                .stream()
                .filter(t -> t.equalsIgnoreCase(transaction))
                .findAny();
        if (optionalTransaction.isEmpty()){
            throw new ApplicationException(Errors.ACCOUNT_CANT_MAKE_TRANSACTION, Map.of("id", accountId));
        }
        return transactionMap.get(optionalTransaction.get());
    }
    private Account retrieveAccount(Long id){
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new ApplicationException(Errors.ACCOUNT_NOT_FOUND,
                        Map.of("id", id)));
    }
    private Account updateStatus(Account account, Transition<AccountDto> transaction, AccountStatus targetStatus) {
        log.trace("Updating account status for account {} to transaction {}: {}", account.getId(), transaction, targetStatus);
        account.setAccountStatus(targetStatus);
        return accountRepository.save(account);
    }



}
