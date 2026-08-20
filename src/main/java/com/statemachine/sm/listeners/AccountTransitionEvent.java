package com.statemachine.sm.listeners;

import com.statemachine.sm.service.AccountStatus;
import com.statemachine.sm.service.transaction.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


@Getter
public class AccountTransitionEvent extends ApplicationEvent {

    private final Object source;
    private final AccountDto accountDto;
    private final AccountStatus accountStatus;
    private final String transition;

    public AccountTransitionEvent(Object source, AccountDto accountDto, AccountStatus accountStatus, String transition) {
        super(source);
        this.source = source;
        this.accountDto = accountDto;
        this.accountStatus = accountStatus;
        this.transition = transition;
    }
}
