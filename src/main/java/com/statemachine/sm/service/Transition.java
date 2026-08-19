package com.statemachine.sm.service;

import com.statemachine.sm.service.transaction.AccountDto;

public interface Transition<T> {

    String getName();

    AccountStatus getTargetStatus();

    void applyProcessing(AccountDto accountDto);
}
