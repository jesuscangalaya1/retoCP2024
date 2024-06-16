package com.test.cp.mapper;

import com.test.cp.dto.request.OrderRequest;
import com.test.cp.dto.response.ClientResponse;
import com.test.cp.dto.response.OrderResponse;
import com.test.cp.entity.ClientEntity;
import com.test.cp.entity.OrderEntity;
import com.test.cp.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-16T10:47:12-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public OrderResponse toDTO(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setClient( clientEntityToClientResponse( orderEntity.getClient() ) );
        orderResponse.setCodigoProducto( orderEntityProductId( orderEntity ) );
        orderResponse.setOrderId( orderEntity.getOrderId() );
        orderResponse.setAmount( orderEntity.getAmount() );

        return orderResponse;
    }

    @Override
    public OrderEntity toEntity(OrderRequest orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setProduct( orderRequestToProductEntity( orderDTO ) );
        orderEntity.setClient( clientMapper.toEntity( orderDTO.getClient() ) );
        orderEntity.setAmount( orderDTO.getAmount() );

        return orderEntity;
    }

    @Override
    public void updateOrderFromDto(OrderRequest orderRequest, OrderEntity orderEntity) {
        if ( orderRequest == null ) {
            return;
        }

        orderEntity.setAmount( orderRequest.getAmount() );
        if ( orderRequest.getClient() != null ) {
            if ( orderEntity.getClient() == null ) {
                orderEntity.setClient( new ClientEntity() );
            }
            clientMapper.updateClientFromDto( orderRequest.getClient(), orderEntity.getClient() );
        }
        else {
            orderEntity.setClient( null );
        }
    }

    @Override
    public List<OrderResponse> toListOrdersDTO(List<OrderEntity> orderEntityList) {
        if ( orderEntityList == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( orderEntityList.size() );
        for ( OrderEntity orderEntity : orderEntityList ) {
            list.add( toDTO( orderEntity ) );
        }

        return list;
    }

    protected ClientResponse clientEntityToClientResponse(ClientEntity clientEntity) {
        if ( clientEntity == null ) {
            return null;
        }

        ClientResponse clientResponse = new ClientResponse();

        clientResponse.setClientId( clientEntity.getClientId() );
        clientResponse.setNames( clientEntity.getNames() );
        clientResponse.setSurnames( clientEntity.getSurnames() );
        clientResponse.setAddress( clientEntity.getAddress() );

        return clientResponse;
    }

    private Long orderEntityProductId(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }
        ProductEntity product = orderEntity.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ProductEntity orderRequestToProductEntity(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( orderRequest.getCodigoProducto() );

        return productEntity;
    }
}
