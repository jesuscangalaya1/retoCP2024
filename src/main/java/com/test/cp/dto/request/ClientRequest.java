package com.test.cp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ClientRequest {

    @Schema(
            description = "Nombre del Cliente",
            example = "Juan Pablo"
    )
    @NotBlank(message = "El nombre no debe estar en blanco")
    @Size(max = 100, min = 5, message = "El nombre debe tener como máximo {max} caracteres y como mínimo {min} caracteres.")
    private String names;

    @Schema(
            description = "Apellidos del Cliente",
            example = "Pérez Sánchez"
    )
    @NotBlank(message = "Los apellidos no deben estar en blanco")
    @Size(max = 100, min = 5, message = "Los apellidos deben tener como máximo {max} caracteres y como mínimo {min} caracteres.")
    private String surnames;

    @Schema(
            description = "Dirección del Cliente",
            example = "Santa clara"
    )
    @NotBlank(message = "El número de teléfono no debe estar en blanco")
    @Size(max = 10, min = 3, message = "El número de teléfono debe tener como máximo {max} caracteres y como mínimo {min} caracteres.")
    private String address;
}
