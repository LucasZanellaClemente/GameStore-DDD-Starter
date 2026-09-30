package br.com.fiap.gamestore.controller;

import br.com.fiap.gamestore.dto.CustomerRequest;
import br.com.fiap.gamestore.entity.Customer;
import br.com.fiap.gamestore.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Customer buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer criar(@Valid @RequestBody CustomerRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public Customer atualizar(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}