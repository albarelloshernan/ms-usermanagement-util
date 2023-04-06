package com.microservice.usermanagement.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountUsrPhonesReqDto {

    @Schema(description = "Número de teléfono.")
    private Long number;

    @Schema(description = "Código postal.")
    private Integer cityCode;

    @Schema(description = "Código de país.")
    private String countryCode;
}
