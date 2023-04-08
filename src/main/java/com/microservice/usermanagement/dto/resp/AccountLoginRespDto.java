package com.microservice.usermanagement.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservice.usermanagement.model.UserPhones;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Nullable
    private String name;

    @Schema(description = "Email del usuario.")
    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.")
    private String email;

    @Schema(description = "Contraseña.")
    @NotEmpty(message = "The password is required.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-_])[a-zA-Z0-9!@#$%^&*].{8,12}$",
            message = "Password must be min 8 and max 12 length containing at least 1 uppercase, " +
                    "1 lowercase, 1 special character and 2 digits ")
    private String password;

    @Schema(description = "Teléfonos del usuario.")
    @Nullable
    private List<UserPhones> phones;
}
