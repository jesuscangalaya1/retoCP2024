package com.test.cp.dto.response;

import lombok.Data;

@Data
public class ClientResponse {

    private Long clientId;
    private String names;
    private String surnames;
    private String address;
}
