package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.converter.AccountEntityConverter;
import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.repository.AccountRepository;
import com.microservice.usermanagement.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @InjectMocks AccountServiceImpl accountServiceImpl;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountEntityConverter accountEntityConverter;

    public AccountServiceImplTest() {
        AccountReqDto accountReqDto = new AccountReqDto();
        User user = new User();
        AccountRespDto accountRespDto = new AccountRespDto();
    }

    @Test
    public void givenNewUser_wheSignUp_thenReturnAccountRespDto(){
        // Given
        AccountReqDto accountReqDto = TestUtils.buildAccountReqDto();
        User user = TestUtils.buildUser();
        Optional<User> optionalUser = TestUtils.buildOptionalUser();
        AccountRespDto accountRespDto = TestUtils.buildAccountRespDto();

        // When
        lenient().when(accountRepository.save(user)).thenReturn(user);
        lenient().when(accountRepository.findOneByEmail(TestUtils.email)).thenReturn(optionalUser);
        optionalUser.ifPresent(value -> lenient().when(accountEntityConverter.fromEntity(value)).thenReturn(accountRespDto));

        // Then
        assertEquals(accountRespDto, accountRespDto);
    }

    @Test
    public void givenNewUser_wheSignUp_thenReturnNull(){
        // Given
        AccountReqDto accountReqDto = TestUtils.buildAccountReqDto();
        User user = TestUtils.buildUser();
        Optional<User> optionalUser = TestUtils.buildOptionalUser();
        AccountRespDto accountRespDto = null;

        // When
        lenient().when(accountRepository.save(user)).thenReturn(user);
        lenient().when(accountRepository.findOneByEmail(TestUtils.email)).thenReturn(optionalUser);
        optionalUser.ifPresent(value -> lenient().when(accountEntityConverter.fromEntity(value)).thenReturn(accountRespDto));

        // Then
        assertNull(accountRespDto, "null");
    }
}