package com.test.cp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class OrderRequest {

    @Schema(
            description = "Cantidad del Pedido",
            example = "10"
    )
    @Positive(message = "El stock debe ser un valor positivo")
    private Integer amount;

    @Schema(
            description = "Id del Producto",
            example = "1"
    )
    @Positive(message = "El id del producto debe ser un valor positivo")
    private Long codigoProducto;

    private ClientRequest client;
}
