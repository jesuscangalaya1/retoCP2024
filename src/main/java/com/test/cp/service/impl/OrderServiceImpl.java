package com.test.cp.service.impl;

import com.test.cp.dto.request.OrderRequest;
import com.test.cp.dto.response.OrderResponse;
import com.test.cp.dto.response.PageableResponse;
import com.test.cp.dto.response.ProductResponse;
import com.test.cp.entity.ClientEntity;
import com.test.cp.entity.OrderEntity;
import com.test.cp.entity.ProductEntity;
import com.test.cp.exceptions.BusinessException;
import com.test.cp.mapper.ClientMapper;
import com.test.cp.mapper.OrderMapper;
import com.test.cp.repository.ClientRepository;
import com.test.cp.repository.OrderRepository;
import com.test.cp.repository.ProductRepository;
import com.test.cp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.test.cp.util.AppConstants.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final ClientMapper clientMapper;

    @Override
    public PageableResponse<OrderResponse> paginationOrders(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina - 1, medidaDePagina, sort);

        // Obtener una página de pedidos desde el repositorio
        Page<OrderEntity> orders = orderRepository.findAllByDeletedFalse(pageable);

        // Mapear la página de entidades a una página de DTOs
        List<OrderResponse> orderResponsePage = orderMapper.toListOrdersDTO(orders.getContent());

        if (orderResponsePage.isEmpty()) {
            throw new BusinessException("P-204", HttpStatus.NO_CONTENT, "Lista Vaciá de pedidos");
        }
        return PageableResponse.<OrderResponse>builder()
                .content(orderResponsePage)
                .pageNumber(orders.getNumber() + 1)
                .pageSize(orders.getSize())
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .last(orders.isLast())
                .build();
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        ProductEntity productEntity = productRepository.findByIdAndDeletedFalse(orderRequest.getCodigoProducto())
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_PRODUCT + orderRequest.getCodigoProducto()));

        ClientEntity clientEntity = clientMapper.toEntity(orderRequest.getClient());
        clientEntity = clientRepository.save(clientEntity);

        OrderEntity orderEntity = orderMapper.toEntity(orderRequest);
        orderEntity.setProduct(productEntity);
        orderEntity.setClient(clientEntity);

        OrderEntity newOrder = orderRepository.save(orderEntity);
        return orderMapper.toDTO(newOrder);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        ProductEntity productEntity = productRepository.findByIdAndDeletedFalse(orderRequest.getCodigoProducto())
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_PRODUCT + orderRequest.getCodigoProducto()));

        OrderEntity orderEntity = orderRepository.findByOrderIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ORDER + id));

        // Mapea y guarda el cliente si es necesario
        ClientEntity clientEntity = clientMapper.toEntity(orderRequest.getClient());
        clientEntity = clientRepository.save(clientEntity);

        // Actualiza los campos de la entidad OrderEntity
        orderEntity.setProduct(productEntity);
        orderEntity.setClient(clientEntity);
        orderEntity.setAmount(orderRequest.getAmount());

        OrderEntity updatedOrder = orderRepository.save(orderEntity);
        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getByIdOrder(Long id) {
        OrderEntity orderEntity = orderRepository.findByOrderIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ORDER + id));
        return orderMapper.toDTO(orderEntity);
    }

    @Override
    public void deleteOrder(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findByOrderIdAndDeletedFalse(id);
        if (orderEntity.isEmpty()) {
            throw new BusinessException(BAD_REQUEST, HttpStatus.NOT_FOUND, BAD_REQUEST_ORDER + id);
        }
        // Desactivar (eliminar lógicamente) un pedido por su ID
        orderRepository.desactivarOrder(id);
    }

}
