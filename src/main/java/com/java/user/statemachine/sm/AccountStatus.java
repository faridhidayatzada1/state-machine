package com.java.user.statemachine;

import com.java.user.statemachine.transaction.Approve;
import com.java.user.statemachine.transaction.Notify;
import com.java.user.statemachine.transaction.Reject;
import com.java.user.statemachine.transaction.Submit;

import java.util.Arrays;
import java.util.List;

public enum AccountStatus {

    DRAFT(Submit.Name),
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
