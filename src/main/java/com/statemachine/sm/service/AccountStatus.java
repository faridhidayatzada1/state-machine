package com.statemachine.sm.service;

import com.statemachine.sm.service.transaction.Approve;
import com.statemachine.sm.service.transaction.Notify;
import com.statemachine.sm.service.transaction.Reject;
import com.statemachine.sm.service.transaction.Submit;

import java.util.Arrays;
import java.util.List;

public enum AccountStatus {

    DRAFT(Submit.NAME),
    IN_REVIEW(Approve.NAME, Reject.NAME),
    APPROVED(Approve.NAME, Notify.NAME),
    NOTIFIED();

    private final List<String> transactions;

    AccountStatus(String...transitions) {
        this.transactions = Arrays.asList(transitions);
    }

    public List<String> getTransactions() {
        return transactions;
    }
}
