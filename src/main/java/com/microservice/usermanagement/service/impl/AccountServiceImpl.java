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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AccountServiceImpl(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public AccountRespDto signUp(AccountReqDto accountReqDto) {
        AccountRespDto response;
        User userEntity;
        String jwtToken;
        if(!accountRepository.existsByUsername(accountReqDto.getName())) {
            jwtToken = jwtTokenProvider.createToken(accountReqDto.getEmail(),accountReqDto.getPassword());
            userEntity = AccountDtoConverter.getInstance().fromDto(accountReqDto);
            userEntity.setPassword(passwordEncoder.encode(accountReqDto.getPassword()));
            userEntity.setJwtToken(jwtToken);
            try{
                this.accountRepository.save(userEntity);
            } catch (Exception e) {
                throw new CustomException("Error al insertar los datos del usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Optional<User> foundUser = accountRepository.findOneByEmail(accountReqDto.getEmail());
        if (!foundUser.isPresent()) {
            throw new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
        response = AccountRespEntityConverter.getInstance().fromEntity(foundUser.get());
        return response;
    }

    public AccountLoginRespDto logIn(String jwtToken, String username, String password) {
        AccountLoginRespDto accountLoginRespDto;
        String parsedToken = jwtTokenProvider.resolveToken(jwtToken);
        String subject = jwtTokenProvider.getUsername(parsedToken);
        Optional<User> optionalUser = accountRepository.findOneByEmail(subject);
        if(optionalUser.isPresent()) {
            accountLoginRespDto = AccountLoginRespEntityConverter.getInstance().fromEntity(optionalUser.get());
        } else {
            throw new CustomException("User not found.", HttpStatus.NOT_FOUND);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            jwtTokenProvider.createToken(username, password);
            return accountLoginRespDto;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
