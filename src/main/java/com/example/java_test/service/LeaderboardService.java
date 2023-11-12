package com.example.java_test.service;

import com.example.java_test.model.Game;
import com.example.java_test.model.Leaderboard;
import com.example.java_test.model.Users;
import com.example.java_test.repository.GameRepository;
import com.example.java_test.repository.LeaderboardRepository;
import com.example.java_test.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final Logger logger = LoggerFactory.getLogger(LeaderboardService.class);

    @Autowired
    public LeaderboardService(LeaderboardRepository leaderboardRepository,
                              UserRepository userRepository,
                              GameRepository gameRepository) {
        this.leaderboardRepository = leaderboardRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<Leaderboard> findAll() {
        return leaderboardRepository.findAll();
    }

    public Leaderboard save(Leaderboard leaderboard) {
        Users user;
        Game game;
        try {
            user = userRepository.findById(leaderboard.getPlayer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + leaderboard.getPlayer().getId()));
            game = gameRepository.findById(leaderboard.getGame().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + leaderboard.getGame().getId()));



        } catch (IllegalArgumentException e) {
            logger.error("Error while fetching user or game: {}", e.getMessage());
            throw e; // Re-throw the exception to be handled further up the call stack
        }

        leaderboard.setPlayer(user);
        leaderboard.setGame(game);

        try {
            return leaderboardRepository.save(leaderboard);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while saving leaderboard: {}", e.getMessage());
            throw new IllegalStateException("Failed to save leaderboard due to data integrity violation", e);
        }
    }

    public Leaderboard update(Long id, Leaderboard updatedLeaderboard) {
        return leaderboardRepository.findById(id).map(existingLeaderboard -> {
            Users user = userRepository.findById(updatedLeaderboard.getPlayer().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Game game = gameRepository.findById(updatedLeaderboard.getGame().getId())
                    .orElseThrow(() -> new RuntimeException("Game not found"));

            existingLeaderboard.setScore(updatedLeaderboard.getScore());
            existingLeaderboard.setPlayer(user);
            existingLeaderboard.setGame(game);

            return leaderboardRepository.save(existingLeaderboard);
        }).orElseThrow(() -> new RuntimeException("Leaderboard entry not found"));
    }

    public void delete(Long id) {
        leaderboardRepository.deleteById(id);
    }

    // Other business methods as needed
}