package com.microservice.usermanagement.converter;

import com.microservice.usermanagement.dto.req.AccountUsrPhonesReqDto;
import com.microservice.usermanagement.model.Phone;

public final class AccountUsrPhonesConverter implements Converter<Phone, AccountUsrPhonesReqDto> {
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
    public Phone fromDto(AccountUsrPhonesReqDto dto) {
        if (dto == null) {
            return null;
        }
        Phone entity = new Phone();
        entity.setPhone(dto.getNumber());
        entity.setCityCode(dto.getCityCode());
        entity.setCountryCode(dto.getCountryCode());
        return entity;
    }

    @Override
    public AccountUsrPhonesReqDto fromEntity(Phone entity) {
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
