package br.com.fiap.gamestore.repository;

import br.com.fiap.gamestore.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByGeneroIgnoreCase(String genero);
}