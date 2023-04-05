package com.microservice.usermanagement.service;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;

public interface AccountService {
    AccountRespDto signUp(AccountReqDto user);
}
