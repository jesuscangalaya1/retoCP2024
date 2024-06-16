package com.test.cp.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuevoUsuario {

    @Schema(
            description = "Nombre de Usuario",
            example = "jesus"
    )
    @NotBlank(message = "nombre de usuario obligatorio")
    private String nombreUsuario;

    @Schema(
            description = "Correo Electrónico",
            example = "jesus@gamil.com"
    )
    @Email(message = "dirección de email no válida")
    @NotBlank(message = "email obligatorio")
    private String email;

    @Schema(
            description = "Contraseña",
            example = "123"
    )
    @NotBlank(message = "contraseña obligatoria")
    private String password;




}

