package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountDtoConverter;
import com.microservice.usermanagement.converter.AccountLoginRespEntityConverter;
import com.microservice.usermanagement.converter.AccountRespEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountErrorDto;
import com.microservice.usermanagement.dto.resp.AccountErrorListDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.exception.CustomException;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import com.microservice.usermanagement.security.JwtTokenProvider;
import com.microservice.usermanagement.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
        AccountErrorDto accountErrorDto = new AccountErrorDto();
        AccountErrorListDto errorListDto = new AccountErrorListDto();
        User userEntity;
        String jwtToken;

        if (!accountRepository.existsByUsername(accountReqDto.getName())) {
            jwtToken = jwtTokenProvider.createToken(accountReqDto.getEmail(), accountReqDto.getPassword());
            userEntity = AccountDtoConverter.getInstance().fromDto(accountReqDto);
            userEntity.setPassword(passwordEncoder.encode(accountReqDto.getPassword()));
            userEntity.setJwtToken(jwtToken);
            try {
                this.accountRepository.save(userEntity);
            } catch (Exception e) {
                accountErrorDto.setTimestamp(LocalDateTime.now().toString());
                accountErrorDto.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.value());
                accountErrorDto.setDetail("Error al insertar datos.");
                errorListDto.setErrorMsg(accountErrorDto);
                throw new CustomException(errorListDto.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Optional<User> optionalUser = accountRepository.findOneByEmail(accountReqDto.getEmail());
        if (!optionalUser.isPresent()) {
            accountErrorDto.setTimestamp(LocalDateTime.now().toString());
            accountErrorDto.setCodigo(HttpStatus.NOT_FOUND.value());
            accountErrorDto.setDetail("User not found.");
            errorListDto.setErrorMsg(accountErrorDto);
            throw new CustomException(errorListDto.getErrorMsg(), HttpStatus.NOT_FOUND);
        }
        response = AccountRespEntityConverter.getInstance().fromEntity(optionalUser.get());
        return response;
    }

    @Override
    @Transactional
    public AccountLoginRespDto logIn(String jwtToken, String username, String password) {

        AccountLoginRespDto response;
        AccountErrorDto accountErrorDto = new AccountErrorDto();
        AccountErrorListDto errorListDto = new AccountErrorListDto();
        String parsedToken = jwtTokenProvider.resolveToken(jwtToken);
        String subject = jwtTokenProvider.getUsername(parsedToken);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            accountErrorDto.setTimestamp(LocalDateTime.now().toString());
            accountErrorDto.setCodigo(HttpStatus.UNPROCESSABLE_ENTITY.value());
            accountErrorDto.setDetail("Wrong username or password.");
            errorListDto.setErrorMsg(accountErrorDto);
            throw new CustomException(errorListDto.getErrorMsg(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<User> optionalUser = accountRepository.findOneByEmail(subject);
        if (optionalUser.isPresent()) {
            optionalUser.get().setJwtToken(jwtTokenProvider.createToken(username, password));
            optionalUser.get().setLastLogin(Date.from(Instant.now()));
            accountRepository.saveAndFlush(optionalUser.get());
            response = AccountLoginRespEntityConverter.getInstance().fromEntity(optionalUser.get());
        } else {
            accountErrorDto.setTimestamp(LocalDateTime.now().toString());
            accountErrorDto.setCodigo(HttpStatus.NOT_FOUND.value());
            accountErrorDto.setDetail("User not found.");
            errorListDto.setErrorMsg(accountErrorDto);
            throw new CustomException(errorListDto.getErrorMsg(), HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
