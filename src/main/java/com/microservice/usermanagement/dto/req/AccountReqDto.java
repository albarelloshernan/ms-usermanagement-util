package com.microservice.usermanagement.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountReqDto {

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
    private List<AccountUsrPhonesReqDto> phones;
}
