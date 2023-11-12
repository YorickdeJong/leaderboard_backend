package com.example.java_test.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
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
        Game game = new Game("Another Game","super leukemia");

        Leaderboard leaderboard = new Leaderboard(score, player, game);

        assertEquals(score, leaderboard.getScore());
        assertEquals(player, leaderboard.getPlayer());
        assertEquals(game, leaderboard.getGame());
        assertEquals(LocalDate.now(), leaderboard.getDate()); // Checking if the date is set to current date
    }
}
