package com.cmput301w23t47.canary.model;

import com.cmput301w23t47.canary.controller.LeaderboardController;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Class for Leaderboard
 */
public class Leaderboard {
    /*Attributes*/
    // the username for player with max QRs
    private String maxQrPlayer;
    // the # of QRs for the winner
    private long maxQr;
    // the username for player max score
    private String maxScorePlayer;
    // the score of winner
    private long maxScore;
    // list of all players to determine ranking
    private ArrayList<LeaderboardPlayer> players; // absent from Firestore
    // player with max score
    private ArrayList<LeaderboardPlayer> byScore; // absent from Firestore
    // player with max qr
    private ArrayList<LeaderboardPlayer> byHighestScoringQr; // absent from Firestore

    public Leaderboard() {}


    /*Getters and Setters*/
    public String getMaxQrPlayer() {
        return maxQrPlayer;
    }

    public void setMaxQrPlayer(String maxQrPlayer) {
        this.maxQrPlayer = maxQrPlayer;
    }

    public long getMaxQr() {
        return maxQr;
    }

    public void setMaxQr(long maxQr) {
        this.maxQr = maxQr;
    }

    public String getMaxScorePlayer() {
        return maxScorePlayer;
    }

    public void setMaxScorePlayer(String maxScorePlayer) {
        this.maxScorePlayer = maxScorePlayer;
    }

    public long getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(long maxScore) {
        this.maxScore = maxScore;
    }

    public ArrayList<LeaderboardPlayer> getPlayers() {
        return players;
    }

    /**
     * Sets the player list; Also sets the player with max score and QR
     * @param players the list of players
     */
    public void setPlayers(ArrayList<LeaderboardPlayer> players) {
        this.players = players;
        this.byScore = LeaderboardController.getPlayersSortedByScore(players);
        this.byHighestScoringQr = LeaderboardController.getPlayersSortedByMaxQr(players);
    }

    public ArrayList<LeaderboardPlayer> getByScore() {
        return byScore;
    }

    public ArrayList<LeaderboardPlayer> getByHighestScoringQr() {
        return byHighestScoringQr;
    }

    @Override
    public String toString() {
        return String.format(Locale.CANADA, "%s %d %s %d", maxScorePlayer, maxScore,
                maxQrPlayer, maxQr);
    }
}
