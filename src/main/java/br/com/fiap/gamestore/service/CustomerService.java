package br.com.fiap.gamestore.service;

import br.com.fiap.gamestore.dto.CustomerRequest;
import br.com.fiap.gamestore.entity.Customer;
import br.com.fiap.gamestore.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> listar() {
        return repository.findAll();
    }

    public Customer buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado: " + id));
    }

    public Customer criar(CustomerRequest request) {
        validarEmailDisponivel(request.email(), null);

        Customer customer = new Customer();
        copiarDados(request, customer);
        return repository.save(customer);
    }

    public Customer atualizar(Long id, CustomerRequest request) {
        Customer customer = buscarPorId(id);            // 404 se não existir
        validarEmailDisponivel(request.email(), id);    // ignora o próprio cliente

        copiarDados(request, customer);
        return repository.save(customer);
    }

    public void excluir(Long id) {
        Customer customer = buscarPorId(id);            // 404 se não existir
        repository.delete(customer);
    }

    private void validarEmailDisponivel(String email, Long idAtual) {
        repository.findByEmailIgnoreCase(email)
                .filter(existente -> !existente.getId().equals(idAtual))
                .ifPresent(existente -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "E-mail já cadastrado: " + email);
                });
    }

    private void copiarDados(CustomerRequest request, Customer customer) {
        customer.setNome(request.nome());
        customer.setEmail(request.email());
    }
}