package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountDtoConverter;
import com.microservice.usermanagement.converter.AccountLoginRespEntityConverter;
import com.microservice.usermanagement.converter.AccountRespEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountErrorDto;
import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
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
import org.springframework.http.ResponseEntity;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> signUp(AccountReqDto accountReqDto) {
        AccountRespDto response;
        JSONObject error = new JSONObject();
        JSONObject mainError = new JSONObject();
        JSONArray errors = new JSONArray();
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
                String excMsg = jsonBuilder("Error al insertar datos.", HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(excMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Optional<User> foundUser = accountRepository.findOneByEmail(accountReqDto.getEmail());
        if (!foundUser.isPresent()) {
            String excMsg = jsonBuilder("User not found.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(excMsg, HttpStatus.NOT_FOUND);
        }
        response = AccountRespEntityConverter.getInstance().fromEntity(userEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> logIn(String jwtToken, String username, String password) {
        AccountLoginRespDto response;
        String parsedToken = jwtTokenProvider.resolveToken(jwtToken);
        String subject = jwtTokenProvider.getUsername(parsedToken);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            String excMsg = jsonBuilder("Wrong username or password.", HttpStatus.UNPROCESSABLE_ENTITY);
            return new ResponseEntity<>(excMsg, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<User> optionalUser = accountRepository.findOneByEmail(subject);
        if (optionalUser.isPresent()) {
            optionalUser.get().setJwtToken(jwtTokenProvider.createToken(username, password));
            optionalUser.get().setLastLogin(Date.from(Instant.now()));
            accountRepository.saveAndFlush(optionalUser.get());
            response = AccountLoginRespEntityConverter.getInstance().fromEntity(optionalUser.get());
        } else {
            String excMsg = jsonBuilder("User not found.", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(excMsg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String jsonBuilder(String detail, HttpStatus httpStatus) {
        AccountErrorDto response = new AccountErrorDto();
        JSONArray errors = new JSONArray();
        JSONObject mainError = new JSONObject();
        response.setTimestamp(LocalDateTime.now().toString());
        response.setCodigo(httpStatus.value());
        response.setDetail(detail);
        errors.appendElement(response);
        mainError.put("error", errors);
        return mainError.toJSONString();
    }
}
