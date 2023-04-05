package com.microservice.usermanagement.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = { "user" })
public class AccountRespDto {
    @Schema(description = "Id del usuario.")
    private Integer id;
    @Schema(description = "Fecha de creación del usuario.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date created;
    @Schema(description = "Fecha de último ingreso.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date lastLogin;
    @Schema(description = "Token de la API.")
    private String token;
    @Schema(description = "Indica si el usuario sigue habilitado dentro del sistema.")
    private boolean isActive;
}
