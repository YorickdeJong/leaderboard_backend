package com.example.java_test.model;


import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "leaderboard")
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

    @Column(name = "date")
    private LocalDate date;

    public Leaderboard() {
        this.date = LocalDate.now(); // Set the current date by default
    }

    public Leaderboard(Float score, Users player, Game game) {
        this.score = score;
        this.player = player;
        this.game = game;
        this.date = LocalDate.now(); // Set the current date
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


    // Getter and setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}