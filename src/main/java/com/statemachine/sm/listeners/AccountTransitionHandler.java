package com.statemachine.sm.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountTransitionHandler implements ApplicationListener<AccountTransitionEvent> {


    @Override
    public void onApplicationEvent(AccountTransitionEvent event) {
        log.info("Account transition event received: {}", event);
    }
}
