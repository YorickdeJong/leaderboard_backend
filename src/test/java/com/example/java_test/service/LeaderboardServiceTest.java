package com.example.java_test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.java_test.model.Game;
import com.example.java_test.model.Leaderboard;
import com.example.java_test.model.Users;
import com.example.java_test.repository.GameRepository;
import com.example.java_test.repository.LeaderboardRepository;
import com.example.java_test.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

class LeaderboardServiceTest {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        leaderboardService.findAll();
        verify(leaderboardRepository).findAll();
    }

    @Test
    void testSave() {
        Long userId = 1L;
        Long gameId = 1L;
        Users user = new Users();
        user.setId(userId);
        Game game = new Game();
        game.setId(gameId);
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setPlayer(user);
        leaderboard.setGame(game);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(leaderboardRepository.save(any(Leaderboard.class))).thenReturn(leaderboard);

        Leaderboard savedLeaderboard = leaderboardService.save(leaderboard);

        assertNotNull(savedLeaderboard);
        verify(userRepository).findById(userId);
        verify(gameRepository).findById(gameId);
        verify(leaderboardRepository).save(leaderboard);
    }

    @Test
    void testUpdate_ExistingLeaderboard() {
        Long leaderboardId = 1L;
        Long userId = 1L;
        Long gameId = 1L;
        Users user = new Users();
        user.setId(userId);
        Game game = new Game();
        game.setId(gameId);
        Leaderboard existingLeaderboard = new Leaderboard();
        existingLeaderboard.setId(leaderboardId);
        existingLeaderboard.setPlayer(user);
        existingLeaderboard.setGame(game);

        Leaderboard updatedLeaderboard = new Leaderboard();
        updatedLeaderboard.setScore(100F);
        updatedLeaderboard.setPlayer(user);
        updatedLeaderboard.setGame(game);

        when(leaderboardRepository.findById(leaderboardId)).thenReturn(Optional.of(existingLeaderboard));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(leaderboardRepository.save(any(Leaderboard.class))).thenReturn(updatedLeaderboard);

        Leaderboard result = leaderboardService.update(leaderboardId, updatedLeaderboard);

        assertNotNull(result);
        assertEquals(100, result.getScore());
        verify(leaderboardRepository).findById(leaderboardId);
        verify(leaderboardRepository).save(existingLeaderboard);
    }

    @Test
    void testUpdate_NonExistingLeaderboard() {
        Long leaderboardId = 1L;
        Leaderboard updatedLeaderboard = new Leaderboard();

        when(leaderboardRepository.findById(leaderboardId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaderboardService.update(leaderboardId, updatedLeaderboard));

        verify(leaderboardRepository).findById(leaderboardId);
    }

    @Test
    void testDelete() {
        Long leaderboardId = 1L;

        leaderboardService.delete(leaderboardId);

        verify(leaderboardRepository).deleteById(leaderboardId);
    }

}
