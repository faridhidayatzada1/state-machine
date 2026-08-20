package com.statemachine.sm.service;

import java.util.List;

public interface TransactionService<T> {

    T transaction (Long id, String transaction);

    List<String> getAllowedTransitions(Long id);
}
