package com.microservice.usermanagement.converter;

import com.microservice.usermanagement.dto.resp.AccountLoginRespDto;
import com.microservice.usermanagement.model.User;

public class AccountLoginRespEntityConverter implements Converter<User, AccountLoginRespDto> {
    private static AccountLoginRespEntityConverter instance = null;

    public AccountLoginRespEntityConverter() {
    }

    public static AccountLoginRespEntityConverter getInstance() {
        if (instance == null) {
            instance = new AccountLoginRespEntityConverter();
        }
        return instance;
    }

    @Override
    public User fromDto(AccountLoginRespDto dto) {
        return null;
    }

    @Override
    public AccountLoginRespDto fromEntity(User entity) {
        if (entity == null) {
            return null;
        }
        AccountLoginRespDto dto = new AccountLoginRespDto();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreatedAt());
        dto.setLastLogin(entity.getLastLogin());
        dto.setActive(entity.isActive());
        dto.setToken(entity.getJwtToken());
        dto.setName(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhones(entity.getUserPhones());
        return dto;
    }
}
