package com.example.transport.ticket.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "vehicle-service", url = "${vehicle.service.url}")
public interface VehicleServiceClient {
    @GetMapping("/routes/{id}")
    Optional<RouteDTO> getRouteById(@PathVariable("id") Long id);
}