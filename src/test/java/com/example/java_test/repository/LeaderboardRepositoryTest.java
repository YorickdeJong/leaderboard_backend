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
        // Assuming Users and Game entities are already tested and working
        Users user = new Users("username", "password", "user@example.com");
        Game game = new Game("GameName", "GameDescription");
        entityManager.persist(user);
        entityManager.persist(game);
        entityManager.flush();

        leaderboard = new Leaderboard(100.0f, user, game);
        entityManager.persist(leaderboard);
        entityManager.flush();
    }

    @Test
    public void whenFindById_thenReturnLeaderboard() {
        Leaderboard found = leaderboardRepository.findById(leaderboard.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(leaderboard.getScore(), found.getScore());
        assertEquals(leaderboard.getPlayer().getName(), found.getPlayer().getName());
        assertEquals(leaderboard.getGame().getName(), found.getGame().getName());
    }

    // Additional tests can be added for other custom methods in LeaderboardRepository
}
