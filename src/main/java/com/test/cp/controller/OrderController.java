package com.test.cp.controller;

import com.test.cp.dto.request.OrderRequest;
import com.test.cp.dto.response.OrderResponse;
import com.test.cp.dto.response.PageableResponse;
import com.test.cp.dto.response.ServiceResponse;
import com.test.cp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static com.test.cp.util.AppConstants.*;

@Validated
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "PEDIDO", description = "Operaciones permitidas sobre la entidad Pedido")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Obtener la información de todos los pedidos paginados")
    @GetMapping(value = "/pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<PageableResponse<OrderResponse>> pageableOrders(
            @RequestParam(value = "pageNo", defaultValue = NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = ORDENAR_POR_DEFECTO_ORDER, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        PageableResponse<OrderResponse> orderPage = orderService.paginationOrders(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), orderPage), HttpStatus.OK).getBody();

    }

    @Operation(summary = "Crear un nuevo pedido")
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<OrderResponse>> createOrder(@RequestBody @Valid OrderRequest orderRequest) {

        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.CREATED), orderResponse), HttpStatus.CREATED);

    }

    @Operation(summary = "Actualizar un pedido existente por su ID")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<OrderResponse>> updateOrder(@Positive(message = ID_POSITIVE)
                                                                      @PathVariable Long id, @RequestBody @Valid OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.updateOrder(id, orderRequest);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), orderResponse), HttpStatus.OK);

    }

    @Operation(summary = "Obtener información de un pedido por su ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse<OrderResponse>> getByIdOrder(@Positive(message = ID_POSITIVE)
                                                                       @PathVariable Long id) {
        OrderResponse orderResponse = orderService.getByIdOrder(id);
        return new ResponseEntity<>(new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), orderResponse), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un pedido por su ID")
    @DeleteMapping(value = "/{id}")
    public ServiceResponse<String> deleteOrder(@Positive(message = ID_POSITIVE)
                                               @PathVariable Long id) {

        orderService.deleteOrder(id);
        return new ServiceResponse<>(SUCCESS,
                String.valueOf(HttpStatus.OK), MESSAGE_ID_ORDER + id + SUCCESS_DELETED);
    }


}
