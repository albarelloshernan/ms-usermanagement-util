package com.microservice.usermanagement.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservice.usermanagement.model.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLoginRespDto {

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

    @Schema(description = "Nombre de usuario.")
    private String name;

    @Schema(description = "Email del usuario.")
    private String email;

    @Schema(description = "Contraseña.")
    private String password;

    @Schema(description = "Teléfonos del usuario.")
    private List<Phone> phones;
}
