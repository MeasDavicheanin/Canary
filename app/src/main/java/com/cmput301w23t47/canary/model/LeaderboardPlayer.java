package com.cmput301w23t47.canary.model;


/**
 * Class for recording the players in the leaderboard
 */
public class LeaderboardPlayer {
    // the username of player
    private String username;
    // the score of player
    private long score;
    // the score of maximum scoring QR for player
    private long maxScoreQr;

    /**
     * Constructor for LeaderboardPlayer
     */
    public LeaderboardPlayer(String username, long score, long maxScoreQr) {
        this.username = username;
        this.score = score;
        this.maxScoreQr = maxScoreQr;
    }

    public String getUsername() {
        return username;
    }

    public long getScore() {
        return score;
    }

    public long getMaxScoreQr() {
        return maxScoreQr;
    }
}

