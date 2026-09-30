package br.com.fiap.gamestore.service;

import br.com.fiap.gamestore.dto.GameRequest;
import br.com.fiap.gamestore.entity.Game;
import br.com.fiap.gamestore.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> listar() {
        return repository.findAll();
    }

    public Game buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Game não encontrado: " + id));
    }

    public Game criar(GameRequest request) {
        Game game = new Game();
        copiarDados(request, game);
        return repository.save(game);
    }

    public Game atualizar(Long id, GameRequest request) {
        Game game = buscarPorId(id);   // lança 404 se não existir
        copiarDados(request, game);    // altera o objeto existente
        return repository.save(game);  // como já tem id, faz UPDATE, não INSERT
    }

    public void excluir(Long id) {
        Game game = buscarPorId(id);   // lança 404 se não existir
        repository.delete(game);
    }

    public List<Game> buscarPorGenero(String genero) {
        return repository.findByGeneroIgnoreCase(genero);
    }

    private void copiarDados(GameRequest request, Game game) {
        game.setTitulo(request.titulo());
        game.setGenero(request.genero());
        game.setPreco(request.preco());
        game.setPlataforma(request.plataforma());
        game.setEstoque(request.estoque());
    }
}