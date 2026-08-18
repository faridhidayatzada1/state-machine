package com.java.user.statemachine.sm.transaction;

import com.java.user.statemachine.sm.AccountStatus;
import com.java.user.statemachine.sm.Transition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Approve implements Transition<AccountDto> {

    public static final String NAME = "approve";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public AccountStatus getTargetStatus() {
        return AccountStatus.APPROVED;
    }

    @Override
    public void applyProcessing(AccountDto accountDto) {
        log.info("Account is transitioning to approved state {}", accountDto.getId());
    }
}
