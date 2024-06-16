package com.test.cp.mapper;

import com.test.cp.dto.request.ClientRequest;
import com.test.cp.dto.response.ClientResponse;
import com.test.cp.entity.ClientEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-16T11:43:33-0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientResponse toDTO(ClientEntity clientEntity) {
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

    @Override
    public ClientEntity toEntity(ClientRequest clientRequest) {
        if ( clientRequest == null ) {
            return null;
        }

        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setNames( clientRequest.getNames() );
        clientEntity.setSurnames( clientRequest.getSurnames() );
        clientEntity.setAddress( clientRequest.getAddress() );

        return clientEntity;
    }

    @Override
    public void updateClientFromDto(ClientRequest clientRequest, ClientEntity clientEntity) {
        if ( clientRequest == null ) {
            return;
        }

        clientEntity.setNames( clientRequest.getNames() );
        clientEntity.setSurnames( clientRequest.getSurnames() );
        clientEntity.setAddress( clientRequest.getAddress() );
    }
}
