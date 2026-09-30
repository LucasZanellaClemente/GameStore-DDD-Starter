package br.com.fiap.gamestore.controller;

import br.com.fiap.gamestore.dto.GameRequest;
import br.com.fiap.gamestore.entity.Game;
import br.com.fiap.gamestore.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@Tag(name = "Games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os games")
    public List<Game> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um game por ID")
    public Game buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/genero/{genero}")
    @Operation(summary = "Lista games por gênero")
    public List<Game> buscarPorGenero(@PathVariable String genero) {
        return service.buscarPorGenero(genero);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um game")
    public Game criar(@Valid @RequestBody GameRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um game")
    public Game atualizar(@PathVariable Long id, @Valid @RequestBody GameRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um game")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}