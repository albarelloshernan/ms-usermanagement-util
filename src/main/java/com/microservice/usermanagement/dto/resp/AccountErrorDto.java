package com.microservice.usermanagement.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountErrorDto {

    @Schema(description = "Fecha actual")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private String timestamp;

    @Schema(description = "Código de error")
    private Integer codigo;

    @Schema(description = "Detalle del error.")
    private String detail;

}
