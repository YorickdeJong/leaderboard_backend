package com.example.java_test.controller;

import com.example.java_test.model.Leaderboard;
import com.example.java_test.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    // Constructor for dependency injection
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    // GET request to retrieve leaderboard entries
    @GetMapping
    public ResponseEntity<List<Leaderboard>> getAllLeaderboardEntries() {
        List<Leaderboard> leaderboardEntries = leaderboardService.findAll();
        return ResponseEntity.ok(leaderboardEntries);
    }

    // POST request to add a new leaderboard entry
    @PostMapping
    public ResponseEntity<Leaderboard> addLeaderboardEntry(@RequestBody Leaderboard leaderboard) {
        Leaderboard newEntry = leaderboardService.save(leaderboard);
        return ResponseEntity.ok(newEntry);
    }

    // PUT request to update a leaderboard entry
    @PutMapping("/{id}")
    public ResponseEntity<Leaderboard> updateLeaderboardEntry(@PathVariable Long id, @RequestBody Leaderboard leaderboard) {
        Leaderboard updatedEntry = leaderboardService.update(id, leaderboard);
        return ResponseEntity.ok(updatedEntry);
    }

    // DELETE request to remove a leaderboard entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaderboardEntry(@PathVariable Long id) {
        leaderboardService.delete(id);
        return ResponseEntity.ok().build();
    }
}