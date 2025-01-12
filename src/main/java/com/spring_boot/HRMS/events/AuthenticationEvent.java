package com.spring_boot.HRMS.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvent {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success){
        log.info("Authentication Successful for the user {}",success.getAuthentication().getName());
    }
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failure){
        log.error("Authentication Failed for the user {} due to {}",failure.getAuthentication().getName(),
                failure.getException().getMessage());
    }
}
