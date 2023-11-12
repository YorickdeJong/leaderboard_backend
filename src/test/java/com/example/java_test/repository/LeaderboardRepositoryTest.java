package com.example.java_test.repository;

import com.example.java_test.model.Game;
import com.example.java_test.model.Leaderboard;
import com.example.java_test.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LeaderboardRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    private Leaderboard leaderboard;



    @BeforeEach
    void setUp() {
        Game game = new Game("Chess", "A strategy board game");
        Users player = new Users("testPlayer", "password", "player@example.com");

        // Persist Game and Users
        entityManager.persist(game);
        entityManager.persist(player);
        entityManager.flush();

        // Retrieve and assert Game and Users exist
        Game persistedGame = entityManager.find(Game.class, game.getId());
        Users persistedUser = entityManager.find(Users.class, player.getId());

        assertNotNull(persistedGame, "Persisted game should not be null");
        assertNotNull(persistedUser, "Persisted user should not be null");
        System.out.println(persistedGame.getName());
        System.out.println(persistedUser.getName());
        // Create and persist Leaderboard
        Leaderboard leaderboard = new Leaderboard(100.0f, player, game);
        entityManager.persist(leaderboard);
        entityManager.flush();
    }




    @Test
    public void whenFindById_thenReturnLeaderboard() {
        Leaderboard found = leaderboardRepository.findById(leaderboard.getId()).orElse(null);

        assertNotNull(found, "Leaderboard should not be null");

        assertEquals(leaderboard.getScore(), found.getScore(), "Scores do not match");

        assertEquals(leaderboard.getPlayer().getName(), found.getPlayer().getName(),
                "Player names do not match");

        assertEquals(leaderboard.getGame().getName(), found.getGame().getName(),
                "Game names do not match");
    }


    // Additional tests can be added for other custom methods in LeaderboardRepository
}
