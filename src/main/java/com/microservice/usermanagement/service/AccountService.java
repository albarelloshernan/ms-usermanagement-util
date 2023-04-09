package com.microservice.usermanagement.service;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<Object> signUp(AccountReqDto accountReqDto);

    ResponseEntity<Object> logIn(String jwtToken, String username, String password);
}
