package com.statemachine.sm.service.transaction;

import com.statemachine.sm.service.AccountStatus;
import com.statemachine.sm.service.Transition;
import io.micrometer.common.util.StringUtils;
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
        log.info("Account is transitioning to approved state {}, {}",NAME, accountDto.getId());

        if (accountDto.getType().equalsIgnoreCase("business") && StringUtils.isNotBlank(accountDto.getIban())) {
            throw new IllegalArgumentException("IBAN is too short for business account");
        }
    }
}
