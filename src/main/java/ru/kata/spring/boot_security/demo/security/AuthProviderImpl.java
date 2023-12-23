package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
