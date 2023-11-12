package com.example.java_test.service;

import com.example.java_test.model.Game;
import com.example.java_test.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Long id, Game gameDetails) {
        return gameRepository.findById(id)
                .map(game -> {
                    game.setName(gameDetails.getName());
                    game.setDescription(gameDetails.getDescription());
                    return gameRepository.save(game);
                }).orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    // Additional methods can be added as needed
}