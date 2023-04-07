package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountDtoConverter;
import com.microservice.usermanagement.converter.AccountEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.exception.CustomException;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import com.microservice.usermanagement.security.JwtTokenProvider;
import com.microservice.usermanagement.service.AccountService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public AccountServiceImpl(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public AccountRespDto signUp(AccountReqDto dto) {
        AccountRespDto response;
        User userEntity = new User();
        String jwtToken;
        if(!accountRepository.existsByUsername(dto.getName())) {
            jwtToken = jwtTokenProvider.createToken(dto.getName());
            userEntity = AccountDtoConverter.getInstance().fromDto(dto);
            userEntity.setJwtToken(jwtToken);
            this.accountRepository.save(userEntity);
        }
        Optional<User> foundUser = this.accountRepository.findOneByEmail(dto.getEmail());
        if (!foundUser.isPresent()) {
            throw new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
        response = AccountEntityConverter.getInstance().fromEntity(foundUser.get());
        return response;
    }
}
