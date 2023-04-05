package com.microservice.usermanagement.converter;

import com.microservice.usermanagement.dto.resp.AccountRespDto;
import com.microservice.usermanagement.model.User;

public class AccountEntityConverter implements Converter<User, AccountRespDto>{
    private static AccountEntityConverter instance = null;
    public AccountEntityConverter() {}
    public static AccountEntityConverter getInstance() {
        if (instance == null) {
            instance = new AccountEntityConverter();
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
        dto.setId(entity.getUserId());
        dto.setCreated(entity.getCreatedAt());
        dto.setLastLogin(entity.getLastLogin());
        dto.setActive(entity.isActive());
        dto.setToken(null);
        return dto;
    }
}
