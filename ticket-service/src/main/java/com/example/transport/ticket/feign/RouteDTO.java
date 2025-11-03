package com.example.transport.ticket.feign;

import lombok.Data;

@Data
public class RouteDTO {
    private Long id;
    private String source;
    private String destination;
    private VehicleDTO vehicle;
}