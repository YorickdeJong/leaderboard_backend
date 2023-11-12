package com.example.java_test.controller;

import com.example.java_test.model.Game;
import com.example.java_test.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // GET request to retrieve all games
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.findAll();
        return ResponseEntity.ok(games);
    }

    // GET request to retrieve a single game by ID
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Game game = gameService.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        return ResponseEntity.ok(game);
    }

    // POST request to add a new game
    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game newGame = gameService.save(game);
        return ResponseEntity.ok(newGame);
    }

    // PUT request to update a game
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        Game updatedGame = gameService.update(id, gameDetails);
        return ResponseEntity.ok(updatedGame);
    }

    // DELETE request to delete a game
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.ok().build();
    }
}