package com.microservice.usermanagement.service;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<Object> signUp(AccountReqDto accountReqDto);
    ResponseEntity<Object> logIn(String jwtToken, String username, String password);
}
