package com.cmput301w23t47.canary;

import static org.junit.Assert.assertEquals;

import com.cmput301w23t47.canary.model.Leaderboard;
import com.cmput301w23t47.canary.model.LeaderboardPlayer;

import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LeaderboardTest {
    private ArrayList<LeaderboardPlayer> players;
    private ArrayList<LeaderboardPlayer> byScore;
    private ArrayList<Long> byHighestScoringQr;
    private Leaderboard leaderboard;
    @Test
    public void testLeaderboard() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        assertEquals(leaderboard.getPlayers(), players);
        assertEquals(leaderboard.getByScore(), byScore);
        assertEquals(leaderboard.getByHighestScoringQr(), byHighestScoringQr);
    }

    @Test
    public void testGetMaxQrPlayer() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getMaxQrPlayer(), "player3");
    }

    @Test
    public void testGetMaxQr() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getMaxQr(), 300);
    }

    @Test
    public void testGetMaxScorePlayer() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getMaxScorePlayer(), "player3");
    }

    @Test
    public void testGetMaxScore() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getMaxScore(), 300);
    }

    @Test
    public void testGetPlayers() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getPlayers(), players);
    }

    @Test
    public void testGetByScore() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getByScore(), byScore);
    }

    @Test
    public void testGetByHighestScoringQr() {
        LeaderboardPlayer player1 = new LeaderboardPlayer("player1", 100, 100);
        LeaderboardPlayer player2 = new LeaderboardPlayer("player2", 200, 200);
        LeaderboardPlayer player3 = new LeaderboardPlayer("player3", 300, 300);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        byScore.add(player3);
        byScore.add(player2);
        byScore.add(player1);
        byHighestScoringQr.add(player3.getMaxScoreQr());
        byHighestScoringQr.add(player2.getMaxScoreQr());
        byHighestScoringQr.add(player1.getMaxScoreQr());
        leaderboard.setPlayers(players);
        assertEquals(leaderboard.getByHighestScoringQr(), byHighestScoringQr);
    }
}


