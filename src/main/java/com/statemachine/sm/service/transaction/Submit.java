package com.statemachine.sm.service.transaction;

import com.statemachine.sm.service.AccountStatus;
import com.statemachine.sm.service.Transition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Submit implements Transition<AccountDto> {

    public static final String NAME = "submit";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public AccountStatus getTargetStatus() {
        return AccountStatus.IN_REVIEW;
    }

    @Override
    public void applyProcessing(AccountDto accountDto) {
        log.info("Account is transitioning to in review state {}, {}",NAME, accountDto.getId());
    }
}