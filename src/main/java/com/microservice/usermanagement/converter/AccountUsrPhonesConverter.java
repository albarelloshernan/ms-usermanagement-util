package com.microservice.usermanagement.converter;

import com.microservice.usermanagement.dto.req.AccountUsrPhonesReqDto;
import com.microservice.usermanagement.model.UserPhones;

public final class AccountUsrPhonesConverter implements Converter<UserPhones, AccountUsrPhonesReqDto> {
    private static AccountUsrPhonesConverter instance = null;

    public AccountUsrPhonesConverter() {
    }

    public static AccountUsrPhonesConverter getInstance() {
        if (instance == null) {
            instance = new AccountUsrPhonesConverter();
        }
        return instance;
    }

    @Override
    public UserPhones fromDto(AccountUsrPhonesReqDto dto) {
        if (dto == null) {
            return null;
        }
        UserPhones entity = new UserPhones();
        entity.setPhone(dto.getNumber());
        entity.setCityCode(dto.getCityCode());
        entity.setCountryCode(dto.getCountryCode());
        return entity;
    }

    @Override
    public AccountUsrPhonesReqDto fromEntity(UserPhones entity) {
        if (entity == null) {
            return null;
        }
        AccountUsrPhonesReqDto dto = new AccountUsrPhonesReqDto();
        dto.setNumber(entity.getPhone());
        dto.setCityCode(entity.getCityCode());
        dto.setCountryCode(entity.getCountryCode());
        return dto;
    }
}
