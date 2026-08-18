package com.java.user.statemachine;

import com.java.user.statemachine.transaction.AccountDto;

public interface Transition<T> {

    String getName();

    AccountStatus getTargetStatus();

    void applyProcessing(AccountDto accountDto);
}
