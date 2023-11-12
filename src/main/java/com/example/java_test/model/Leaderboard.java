package com.example.java_test.model;


import jakarta.persistence.*;

@Entity
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float score;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users player;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Leaderboard() {
    }

    public Leaderboard(Float score, Users player, Game game) {
        this.score = score;
        this.player = player;
        this.game = game;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Users getPlayer() {
        return player;
    }

    public void setPlayer(Users player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}