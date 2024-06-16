package com.test.cp.mapper;

import com.test.cp.dto.request.OrderRequest;
import com.test.cp.dto.request.ProductRequest;
import com.test.cp.dto.response.OrderResponse;
import com.test.cp.entity.OrderEntity;
import com.test.cp.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, ClientMapper.class})
public interface OrderMapper {
    @Mapping(source = "product.id", target = "codigoProducto")
    @Mapping(source = "client.clientId", target = "client.clientId")
    OrderResponse toDTO(OrderEntity orderEntity);
    @Mapping(source = "codigoProducto", target = "product.id")
    @Mapping(source = "client", target = "client")
    @Mapping(target = "deleted", ignore = true)
    OrderEntity toEntity(OrderRequest orderDTO);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateOrderFromDto(OrderRequest orderRequest, @MappingTarget OrderEntity orderEntity);

    List<OrderResponse> toListOrdersDTO(List<OrderEntity> orderEntityList);
}


