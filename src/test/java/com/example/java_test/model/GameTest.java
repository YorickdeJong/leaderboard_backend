package com.example.java_test_real.model;

import com.example.java_test.model.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testGameGettersAndSetters() {
        Long id = 1L;
        String name = "Chess";
        String description = "A strategy board game";

        Game game = new Game();
        game.setId(id);
        game.setName(name);
        game.setDescription(description);

        assertEquals(id, game.getId());
        assertEquals(name, game.getName());
        assertEquals(description, game.getDescription());
    }

    @Test
    void testGameConstructor() {
        String name = "Checkers";
        String description = "A draughts board game";

        Game game = new Game(name, description);

        assertNull(game.getId()); // ID is not set through the constructor
        assertEquals(name, game.getName());
        assertEquals(description, game.getDescription());
    }
}
