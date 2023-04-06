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

    public static String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdXRoX3RpbWUiOjE2NzM0NDcxMzMsImp0aSI6Ijg2" +
            "ZThhNTJlLWI0OGYtNGNkNS04YmYzLTI1ZjA2MGNlMTc4ZCIsImlzcyI6Imh0dHBzOi8vdGVzdC1sb2dpbi5jbG" +
            "Fyby5jb20uYXIvYXV0aC9yZWFsbXMvY2xhcm8iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZjp1bXMtYXI6ZGEx" +
            "OGFiNGQtYmUxYi00YTdhLThjY2UtZjcxMjU4ZDA5ZGEzIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiMDE5ZTIyYz" +
            "EiLCJub25jZSI6IjAwYTU2MWMzNzgwZThkN2JiM2NmZGIyZDk3ZWJmOWFiIiwic2Vzc2lvbl9zdGF0ZSI6Ijdm" +
            "NWViOTE3LWI5ZDgtNDA0My1iZTZmLWU4MmZlODBjZWZmYyIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZm" +
            "ZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6" +
            "eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl" +
            "19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBjbGFyb19wcm9maWxlIGVtYWlsIiwic2lkIjoiN2Y1ZWI5MTct" +
            "YjlkOC00MDQzLWJlNmYtZTgyZmU4MGNlZmZjIiwiZ2xvYmFsX3VzZXJfaWQiOiJkYTE4YWI0ZC1iZTFiLTRhN2" +
            "EtOGNjZS1mNzEyNThkMDlkYTMiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwidXNlcl9pZCI6IjQwMjM1MyIsInBy" +
            "ZWZlcnJlZF91c2VybmFtZSI6InNjYXJkb3pvKzEwNkB2YXRlcy5jb20iLCJkYXlzX3NpbmNlX2NyZWF0aW9uIj" +
            "oiMTEzIiwiZW1haWwiOiJzY2FyZG96bysxMDZAdmF0ZXMuY29tIn0.pQlWXOKcWAweXEf8Hkn9VVtY04aQv4t5" +
            "snrXIRIBFjo";
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
