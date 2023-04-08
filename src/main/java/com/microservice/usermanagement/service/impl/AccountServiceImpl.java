package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountDtoConverter;
import com.microservice.usermanagement.converter.AccountLoginRespEntityConverter;
import com.microservice.usermanagement.converter.AccountRespEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.exception.CustomException;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import com.microservice.usermanagement.security.JwtTokenProvider;
import com.microservice.usermanagement.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AccountRespDto signUp(AccountReqDto accountReqDto) {
        AccountRespDto response;
        JSONObject error = new JSONObject();
        JSONObject mainError = new JSONObject();
        JSONArray errors = new JSONArray();
        User userEntity = new User();
        String jwtToken;
        if(!accountRepository.existsByUsername(accountReqDto.getName())) {
            jwtToken = jwtTokenProvider.createToken(accountReqDto.getEmail(),accountReqDto.getPassword());
            userEntity = AccountDtoConverter.getInstance().fromDto(accountReqDto);
            userEntity.setPassword(passwordEncoder.encode(accountReqDto.getPassword()));
            userEntity.setJwtToken(jwtToken);
            try{
                this.accountRepository.save(userEntity);
            } catch (Exception e) {
                String excMsg = jsonBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar datos.");
                throw new CustomException(excMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Optional<User> foundUser = accountRepository.findOneByEmail(accountReqDto.getEmail());
        if (!foundUser.isPresent()) {
            String excMsg = jsonBuilder(HttpStatus.NOT_FOUND, "User not found.");
            throw new CustomException(excMsg, HttpStatus.NOT_FOUND);
        }
        response = AccountRespEntityConverter.getInstance().fromEntity(userEntity);
        return response;
    }

    public AccountLoginRespDto logIn(String jwtToken, String username, String password) {
        AccountLoginRespDto accountLoginRespDto;
        String parsedToken = jwtTokenProvider.resolveToken(jwtToken);
        String subject = jwtTokenProvider.getUsername(parsedToken);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            String excMsg = jsonBuilder(HttpStatus.UNPROCESSABLE_ENTITY, "Wrong username or password.");
            throw new CustomException(excMsg, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<User> optionalUser = accountRepository.findOneByEmail(subject);
        if(optionalUser.isPresent()) {
            optionalUser.get().setJwtToken(jwtTokenProvider.createToken(username, password));
            optionalUser.get().setLastLogin(Date.from(Instant.now()));
            accountRepository.saveAndFlush(optionalUser.get());
            accountLoginRespDto = AccountLoginRespEntityConverter.getInstance().fromEntity(optionalUser.get());
        } else {
            String excMsg = jsonBuilder(HttpStatus.NOT_FOUND, "User not found.");
            throw new CustomException(excMsg, HttpStatus.NOT_FOUND);
        }
        return accountLoginRespDto;
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
