package com.example.transport.vehicle.controller;

import com.example.transport.vehicle.model.Route;
import com.example.transport.vehicle.repository.RouteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteRepository routeRepository;

    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    // Add the @PathVariable("id") annotation here
    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable("id") Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Route createRoute(@RequestBody Route route) {
        return routeRepository.save(route);
    }
}