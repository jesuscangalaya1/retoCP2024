package com.test.cp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long orderId;
    private Integer amount;
    private Long codigoProducto;
    private ClientResponse client;
}
