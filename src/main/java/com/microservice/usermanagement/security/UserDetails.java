package com.microservice.usermanagement.security;

import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            String excMsg = jsonBuilder(HttpStatus.NOT_FOUND, "User '" + username + "' not found.");
            throw new UsernameNotFoundException(excMsg);
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

    private String jsonBuilder(HttpStatus httpStatus, String detail) {
        JSONObject error = new JSONObject();
        JSONObject mainError = new JSONObject();
        JSONArray errors = new JSONArray();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("codigo", httpStatus.value());
        error.put("detail", detail);
        errors.appendElement(error);
        mainError.put("error",errors);
        return mainError.toJSONString();
    }
}
