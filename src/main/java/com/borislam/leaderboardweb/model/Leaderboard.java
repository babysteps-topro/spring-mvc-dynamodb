package com.borislam.leaderboardweb.model;

public class Leaderboard {
    private String userId;
    private String game;
    private String userName;
    private String location;
    private Integer topScore;
    private String scoreDate;

    public Leaderboard(String userId, String game, String userName, String location, Integer topScore, String scoreDate) {
        this.userId = userId;
        this.game = game;
        this.userName = userName;
        this.location = location;
        this.topScore = topScore;
        this.scoreDate = scoreDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTopScore() {
        return topScore;
    }

    public void setTopScore(Integer topScore) {
        this.topScore = topScore;
    }

    public String getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(String scoreDate) {
        this.scoreDate = scoreDate;
    }
}
