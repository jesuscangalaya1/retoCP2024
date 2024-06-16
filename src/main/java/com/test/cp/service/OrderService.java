package com.test.cp.service;

import com.test.cp.dto.request.OrderRequest;
import com.test.cp.dto.response.OrderResponse;
import com.test.cp.dto.response.PageableResponse;

public interface OrderService {

    PageableResponse<OrderResponse> paginationOrders(int numeroDePagina, int medidaDePagina,
                                                       String ordenarPor, String sortDir);

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse updateOrder(Long id, OrderRequest orderRequest);

    OrderResponse getByIdOrder(Long id);

    void deleteOrder(Long id);

}
