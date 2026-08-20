package com.statemachine.sm;

import com.statemachine.sm.domain.Account;
import com.statemachine.sm.repository.AccountRepository;
import com.statemachine.sm.service.AccountStatus;
import com.statemachine.sm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class StateMachineApplication implements CommandLineRunner {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Account account = new Account();
        account.setIban("123345");
        account.setName("Test");
        account.setAccountStatus(AccountStatus.DRAFT);
        account.setType("SAVING");
        accountRepository.save(account);

        transactionService.transaction(account.getId(), "submit");
        transactionService.transaction(account.getId(), "approve");
        transactionService.transaction(account.getId(), "approve");
        transactionService.transaction(account.getId(), "approve");
        transactionService.transaction(account.getId(), "approve");
        transactionService.transaction(account.getId(), "notify");
        //transactionService.transaction(account.getId(), "notify");
    }
}
