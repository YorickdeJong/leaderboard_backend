package com.example.java_test_real.model;

import com.example.java_test.model.Game;
import com.example.java_test.model.Leaderboard;
import com.example.java_test.model.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LeaderboardTest {
    @Test
    void testLeaderboardGettersAndSetters() {
        Long id = 1L;
        Float score = 100.0f;
        Users player = new Users("testPlayer", "password", "player@example.com");
        Game game = new Game("Test Game", "Description of Test Game");

        Leaderboard leaderboard = new Leaderboard();

        leaderboard.setId(id);
        leaderboard.setScore(score);
        leaderboard.setPlayer(player);
        leaderboard.setGame(game);

        assertEquals(id, leaderboard.getId());
        assertEquals(score, leaderboard.getScore());
        assertEquals(player, leaderboard.getPlayer());
        assertEquals(game, leaderboard.getGame());
        assertNotNull(leaderboard.getDate()); // Checking if date is set
    }

    @Test
    void testLeaderboardConstructor() {
        Float score = 200.0f;
        Users player = new Users("anotherPlayer", "password123", "another@example.com");
        Game game = new Game("Another Game");

        Leaderboard leaderboard = new Leaderboard(score, player, game);

        assertEquals(score, leaderboard.getScore());
        assertEquals(player, leaderboard.getPlayer());
        assertEquals(game, leaderboard.getGame());
        assertEquals(LocalDate.now(), leaderboard.getDate()); // Checking if the date is set to current date
    }
}
