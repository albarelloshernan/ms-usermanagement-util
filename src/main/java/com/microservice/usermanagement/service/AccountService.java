package com.microservice.usermanagement.service;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;

public interface AccountService {
    AccountRespDto signUp(AccountReqDto accountReqDto);
    AccountLoginRespDto logIn(String jwtToken, String username, String password);
}
