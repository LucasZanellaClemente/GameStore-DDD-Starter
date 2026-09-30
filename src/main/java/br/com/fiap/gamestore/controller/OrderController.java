package br.com.fiap.gamestore.controller;

import br.com.fiap.gamestore.dto.OrderRequest;
import br.com.fiap.gamestore.entity.Order;
import br.com.fiap.gamestore.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> listar() {
        return service.listar();
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> listarPorCliente(@PathVariable Long customerId) {
        return service.listarPorCliente(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order criar(@Valid @RequestBody OrderRequest request) {
        return service.criar(request);
    }
}