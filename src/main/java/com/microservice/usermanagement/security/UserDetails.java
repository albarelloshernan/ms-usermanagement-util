package com.microservice.usermanagement.security;

import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetails implements UserDetailsService {
    @Autowired
    private final AccountRepository accountRepository;

    private final String ROLE_ADMIN = "ADMIN";

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        final Optional<User> opUser = accountRepository.findOneByEmail(username);

        if (!opUser.isPresent()) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(opUser.get().getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .authorities(ROLE_ADMIN)
                .build();
    }
}
