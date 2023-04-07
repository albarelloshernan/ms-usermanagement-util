package com.microservice.usermanagement.util;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.dto.req.AccountUsrPhonesReqDto;
import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.model.User;
import com.microservice.usermanagement.model.UserPhones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TestUtils {
    public static String name = "Mi Nombre y Apellido";
    public static String email = "firmapersonalizada@dominio.com";
    public static String password = "a2asfGfdfdf4";
    public static Long number = 1166778899L;
    public static Integer cityCode = 9;
    public static String countryCode = "+54";
    public static Integer userId = 1;
    public static SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
    public static Date created;
    static {
        try {
            created = dateFor.parse("03/04/2023");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Date lastLogin;
    static {
        try {
            lastLogin = dateFor.parse("04/04/2023");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIZXJuYW4iLCJpYXQiOjE2ODA4NzI1NjAs" +
            "ImV4cCI6MTY4MDg3NjE2MH0.zDn9ZRvD6Jn3kpTq9Gd9jK56jIYP0xxqXqZIMQ76wsk";
    public static boolean active = true;

    public static AccountReqDto buildAccountReqDto(){
        AccountReqDto accountReqDto = new AccountReqDto();
        AccountUsrPhonesReqDto accountUsrPhonesReqDto = new AccountUsrPhonesReqDto();
        List<AccountUsrPhonesReqDto> accountUsrPhonesReqDtoList = new ArrayList<>();
        accountReqDto.setName(name);
        accountReqDto.setEmail(email);
        accountReqDto.setPassword(password);
        accountUsrPhonesReqDto.setNumber(number);
        accountUsrPhonesReqDto.setCityCode(cityCode);
        accountUsrPhonesReqDto.setCountryCode(countryCode);
        accountUsrPhonesReqDtoList.add(0, accountUsrPhonesReqDto);
        accountReqDto.setPhones(accountUsrPhonesReqDtoList);
        return accountReqDto;
    }
    public static User buildUser() {
        UserPhones usrPhones = new UserPhones();
        List<UserPhones> usrPhonesList = new ArrayList<>();
        User user = new User();
        user.setUserId(userId);
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        usrPhones.setPhone(number);
        usrPhones.setCityCode(cityCode);
        usrPhones.setCountryCode(countryCode);
        usrPhonesList.add(0, usrPhones);
        user.setUserPhones(usrPhonesList);
        user.setJwtToken(token);
        return user;
    }
    public static Optional<User> buildOptionalUser() {
        UserPhones usrPhones = new UserPhones();
        List<UserPhones> usrPhonesList = new ArrayList<>();
        User user = new User();
        user.setUserId(userId);
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        usrPhones.setPhone(number);
        usrPhones.setCityCode(cityCode);
        usrPhones.setCountryCode(countryCode);
        usrPhonesList.add(0, usrPhones);
        user.setUserPhones(usrPhonesList);
        user.setJwtToken(token);
        return Optional.of(user);
    }
    public static AccountRespDto buildAccountRespDto() {
        AccountRespDto accountRespDto = new AccountRespDto();
        accountRespDto.setId(userId);
        accountRespDto.setCreated(created);
        accountRespDto.setLastLogin(lastLogin);
        accountRespDto.setToken(token);
        accountRespDto.setActive(active);
        return accountRespDto;
    }
}
