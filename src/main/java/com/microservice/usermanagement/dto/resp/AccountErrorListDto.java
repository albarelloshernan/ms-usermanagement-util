package com.microservice.usermanagement.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountErrorListDto {

    @Schema(description = "Listado de errores.")
    private JSONObject errorMsg = new JSONObject();

    public String getErrorMsg() {
        return errorMsg.toJSONString();
    }

    public void setErrorMsg(AccountErrorDto error) {
        List<AccountErrorDto> accountErrorDtoList = new ArrayList<>();
        accountErrorDtoList.add(error);
        errorMsg.put("error", accountErrorDtoList);
        this.errorMsg = errorMsg;
    }
}
