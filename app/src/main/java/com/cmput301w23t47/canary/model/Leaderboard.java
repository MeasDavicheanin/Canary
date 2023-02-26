package com.cmput301w23t47.canary.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Class for Leaderboard
 */
public class Leaderboard {
    /**
     * Class for recording the players in the leaderboard
     */
    public static class LeaderboardPlayer {
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

    public Leaderboard() {}

    // the username for player with max QRs
    private String maxQrPlayer;
    // the # of QRs for the winner
    private long maxQr;
    // the username for player max score
    private String maxScorePlayer;
    // the score of winner
    private long maxScore;
    // list of all players to determine ranking
    private ArrayList<LeaderboardPlayer> players;

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

    public void setPlayers(ArrayList<LeaderboardPlayer> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return String.format(Locale.CANADA, "%s %d %s %d", maxScorePlayer, maxScore,
                maxQrPlayer, maxQr);
    }
}
