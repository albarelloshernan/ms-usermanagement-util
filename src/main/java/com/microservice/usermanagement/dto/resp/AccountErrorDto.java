package com.microservice.usermanagement.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountErrorDto {
    @Schema(description = "Fecha actual")
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    @Schema(description = "Código de error")
    private Integer codigo;
    @Schema(description = "Detalle del error.")
    private String detail;
}
