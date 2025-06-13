package com.server.join.domain;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private Long userId;
    private String city;
    private String address;
}