package com.microservice.usermanagement.converter;

import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.model.User;

public class AccountRespEntityConverter implements Converter<User, AccountRespDto> {
    private static AccountRespEntityConverter instance = null;

    public AccountRespEntityConverter() {
    }

    public static AccountRespEntityConverter getInstance() {
        if (instance == null) {
            instance = new AccountRespEntityConverter();
        }
        return instance;
    }

    @Override
    public User fromDto(AccountRespDto dto) {
        return null;
    }

    @Override
    public AccountRespDto fromEntity(User entity) {
        if (entity == null) {
            return null;
        }
        AccountRespDto dto = new AccountRespDto();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreatedAt());
        dto.setLastLogin(entity.getLastLogin());
        dto.setActive(entity.isActive());
        dto.setToken(entity.getJwtToken());
        return dto;
    }
}
