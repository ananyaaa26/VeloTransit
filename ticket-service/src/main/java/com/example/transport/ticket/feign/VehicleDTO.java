package com.example.transport.ticket.feign;

import lombok.Data;

@Data
public class VehicleDTO {
    private Long id;
    private String type;
    private int capacity;
}