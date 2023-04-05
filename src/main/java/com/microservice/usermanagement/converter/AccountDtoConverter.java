package com.microservice.usermanagement.converter;

import com.microservice.usermanagement.dto.req.AccountReqDto;
import com.microservice.usermanagement.model.User;

public final class AccountDtoConverter implements Converter<User, AccountReqDto>{
    private static AccountDtoConverter instance = null;
    public AccountDtoConverter() {}
    public static AccountDtoConverter getInstance() {
        if (instance == null) {
            instance = new AccountDtoConverter();
        }
        return instance;
    }

    @Override
    public User fromDto(AccountReqDto dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setUserPhones(AccountUsrPhonesConverter.getInstance().fromDto(dto.getPhones()));
        entity.setActive(true);
        return entity;
    }

    @Override
    public AccountReqDto fromEntity(User entity) {
        if (entity == null) {
            return null;
        }
        AccountReqDto dto = new AccountReqDto();
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhones(AccountUsrPhonesConverter.getInstance().fromEntity(entity.getUserPhones()));
        return dto;
    }
}
