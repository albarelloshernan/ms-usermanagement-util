package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountDtoConverter;
import com.microservice.usermanagement.converter.AccountEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.exception.AccountException;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import com.microservice.usermanagement.service.AccountService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountRespDto signUp(AccountReqDto dto) {
        AccountRespDto response;
        try {
            this.accountRepository.save(AccountDtoConverter.getInstance().fromDto(dto));
        } catch (Exception e) {
            LOGGER.error("Account creation failed.");
            throw new AccountException();
        }
        Optional<User> foundUser = this.accountRepository.findOneByEmail(dto.getEmail());
        if (!foundUser.isPresent()) {
            return null;
        }
        response = AccountEntityConverter.getInstance().fromEntity(foundUser.get());
        return response;
    }
}
