package com.test.cp.mapper;

import com.test.cp.dto.request.ClientRequest;
import com.test.cp.dto.response.ClientResponse;
import com.test.cp.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponse toDTO(ClientEntity clientEntity);
    ClientEntity toEntity(ClientRequest clientRequest);
    void updateClientFromDto(ClientRequest clientRequest, @MappingTarget ClientEntity clientEntity);
}
