package org.example.cpt402cw3.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Register Request")
public class RegisterRequest {
    @NotBlank(message = "Username cannot be blank")
    @Schema(description = "Username", example = "user123")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Password", example = "123456")
    private String password;

    @Schema(description = "Email", example = "user@example.com")
    private String email;
}