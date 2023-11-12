package com.example.java_test.service;

import com.example.java_test.model.Leaderboard;
import com.example.java_test.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    @Autowired
    public LeaderboardService(LeaderboardRepository leaderboardRepository) {
        this.leaderboardRepository = leaderboardRepository;
    }

    public List<Leaderboard> findAll() {
        return leaderboardRepository.findAll();
    }

    public Leaderboard save(Leaderboard leaderboard) {
        return leaderboardRepository.save(leaderboard);
    }

    public Leaderboard update(Long id, Leaderboard leaderboard) {
        return leaderboardRepository.findById(id).map(existingEntry -> {
            // Copy properties from the provided leaderboard to the existingEntry
            existingEntry.setScore(leaderboard.getScore());
            existingEntry.setPlayer(leaderboard.getPlayer());
            existingEntry.setGame(leaderboard.getGame());
            return leaderboardRepository.save(existingEntry);
        }).orElseThrow(() -> new RuntimeException("Leaderboard entry not found"));
    }

    public void delete(Long id) {
        leaderboardRepository.deleteById(id);
    }

    // Other business methods as needed
}