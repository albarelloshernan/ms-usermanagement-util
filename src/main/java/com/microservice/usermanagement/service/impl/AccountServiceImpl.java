package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountDtoConverter;
import com.microservice.usermanagement.converter.AccountLoginRespEntityConverter;
import com.microservice.usermanagement.converter.AccountRespEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountErrorDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.exception.CustomException;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import com.microservice.usermanagement.security.JwtTokenProvider;
import com.microservice.usermanagement.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
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
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountRespDto signUp(AccountReqDto accountReqDto) {
        AccountRespDto response;
        AccountErrorDto errorDto = new AccountErrorDto();
        JSONObject errorList = new JSONObject();
        User userEntity = new User();
        String jwtToken;
        if (!accountRepository.existsByUsername(accountReqDto.getName())) {
            jwtToken = jwtTokenProvider.createToken(accountReqDto.getEmail(), accountReqDto.getPassword());
            userEntity = AccountDtoConverter.getInstance().fromDto(accountReqDto);
            userEntity.setPassword(passwordEncoder.encode(accountReqDto.getPassword()));
            userEntity.setJwtToken(jwtToken);
            try {
                this.accountRepository.save(userEntity);
            } catch (Exception e) {
                errorDto.setTimestamp(LocalDateTime.now().toString());
                errorDto.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
                errorDto.setDetail("Error al insertar datos.");
                errorList.put("error", errorDto);
                throw new CustomException(errorList.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Optional<User> foundUser = accountRepository.findOneByEmail(accountReqDto.getEmail());
        if (!foundUser.isPresent()) {
            errorDto.setTimestamp(LocalDateTime.now().toString());
            errorDto.setCodigo(HttpStatus.NOT_FOUND.value());
            errorDto.setDetail("User not found.");
            errorList.put("error", errorDto);
            throw new CustomException(errorList.toJSONString(), HttpStatus.NOT_FOUND);
        }
        response = AccountRespEntityConverter.getInstance().fromEntity(userEntity);
        return response;
    }

    @Override
    @Transactional
    public AccountLoginRespDto logIn(String jwtToken, String username, String password) {
        AccountLoginRespDto response;
        AccountErrorDto errorDto = new AccountErrorDto();
        JSONObject errorList = new JSONObject();
        String parsedToken = jwtTokenProvider.resolveToken(jwtToken);
        String subject = jwtTokenProvider.getUsername(parsedToken);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            errorDto.setTimestamp(LocalDateTime.now().toString());
            errorDto.setCodigo(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorDto.setDetail("Wrong username or password.");
            errorList.put("error", errorDto);
            throw new CustomException(errorList.toJSONString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<User> optionalUser = accountRepository.findOneByEmail(subject);
        if (optionalUser.isPresent()) {
            optionalUser.get().setJwtToken(jwtTokenProvider.createToken(username, password));
            optionalUser.get().setLastLogin(Date.from(Instant.now()));
            accountRepository.saveAndFlush(optionalUser.get());
            response = AccountLoginRespEntityConverter.getInstance().fromEntity(optionalUser.get());
        } else {
            errorDto.setTimestamp(LocalDateTime.now().toString());
            errorDto.setCodigo(HttpStatus.NOT_FOUND.value());
            errorDto.setDetail("User not found.");
            errorList.put("error", errorDto);
            throw new CustomException(errorList.toJSONString(), HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
