package br.com.fiap.gamestore.service;

import br.com.fiap.gamestore.dto.OrderRequest;
import br.com.fiap.gamestore.entity.Customer;
import br.com.fiap.gamestore.entity.Game;
import br.com.fiap.gamestore.entity.Order;
import br.com.fiap.gamestore.repository.CustomerRepository;
import br.com.fiap.gamestore.repository.GameRepository;
import br.com.fiap.gamestore.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final GameRepository gameRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        GameRepository gameRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    public List<Order> listar() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order criar(OrderRequest request) {
        // 1. Buscar cliente
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado: " + request.customerId()));

        // 2. Buscar game
        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Game não encontrado: " + request.gameId()));

        // 3. Validar estoque
        if (game.getEstoque() < request.quantidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Estoque insuficiente. Disponível: " + game.getEstoque()
                            + ", solicitado: " + request.quantidade());
        }

        // 4. Calcular valorTotal = preço * quantidade
        BigDecimal valorTotal = game.getPreco()
                .multiply(BigDecimal.valueOf(request.quantidade()));

        // 5. Baixar estoque
        game.setEstoque(game.getEstoque() - request.quantidade());
        gameRepository.save(game);

        // 6. Montar e persistir o pedido
        Order order = new Order();
        order.setCustomer(customer);
        order.setGame(game);
        order.setQuantidade(request.quantidade());
        order.setValorTotal(valorTotal);
        order.setDataPedido(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public List<Order> listarPorCliente(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cliente não encontrado: " + customerId);
        }
        return orderRepository.findByCustomerId(customerId);
    }
}