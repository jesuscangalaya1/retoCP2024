package com.test.cp.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUsuario {

    @Schema(
            description = "Nombre de Usuario",
            example = "jesus"
    )
    @NotBlank(message = "nombre de usuario/email obligatorio")
    private String nombreUsuario;

    @Schema(
            description = "Contraseña del Usuario",
            example = "123"
    )
    @NotBlank(message = "contraseña obligatoria")
    private String password;


}
