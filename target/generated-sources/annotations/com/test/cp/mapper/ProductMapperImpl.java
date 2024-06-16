package com.test.cp.mapper;

import com.test.cp.dto.request.ProductRequest;
import com.test.cp.dto.response.ProductResponse;
import com.test.cp.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-16T10:47:12-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toProductDTO(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId( productEntity.getId() );
        productResponse.setName( productEntity.getName() );
        productResponse.setDescription( productEntity.getDescription() );
        productResponse.setPrice( productEntity.getPrice() );
        productResponse.setStock( productEntity.getStock() );

        return productResponse;
    }

    @Override
    public ProductEntity toProductEntity(ProductRequest productRequest) {
        if ( productRequest == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName( productRequest.getName() );
        productEntity.setDescription( productRequest.getDescription() );
        productEntity.setPrice( productRequest.getPrice() );
        productEntity.setStock( productRequest.getStock() );

        return productEntity;
    }

    @Override
    public void updateProductFromDto(ProductRequest productRequest, ProductEntity productEntity) {
        if ( productRequest == null ) {
            return;
        }

        productEntity.setName( productRequest.getName() );
        productEntity.setDescription( productRequest.getDescription() );
        productEntity.setPrice( productRequest.getPrice() );
        productEntity.setStock( productRequest.getStock() );
    }

    @Override
    public List<ProductResponse> toListProductsDTO(List<ProductEntity> productEntityList) {
        if ( productEntityList == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( productEntityList.size() );
        for ( ProductEntity productEntity : productEntityList ) {
            list.add( toProductDTO( productEntity ) );
        }

        return list;
    }
}
