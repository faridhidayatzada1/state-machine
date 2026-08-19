package com.statemachine.sm.repository;

import com.statemachine.sm.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
