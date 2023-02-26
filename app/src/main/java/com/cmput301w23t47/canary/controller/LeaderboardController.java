package com.cmput301w23t47.canary.controller;

import com.cmput301w23t47.canary.model.Leaderboard;
import com.cmput301w23t47.canary.repository.PlayerRepository;

import java.util.ArrayList;

/**
 * Controller class for Leaderboard
 */
public class LeaderboardController {
    /**
     * Gets the leaderboard player list from the repo stored in firestore
     * @param playerRepoList ArrayList(LeaderboardPlayer): the list of players in firestore model
     * @return mapped list of LeaderboardPlayer models
     */
    public static ArrayList<Leaderboard.LeaderboardPlayer> getLeaderboardPlayerList(
            ArrayList<PlayerRepository> playerRepoList
    ) {
        ArrayList<Leaderboard.LeaderboardPlayer> playerList = new ArrayList<>();
        for (PlayerRepository playerRepo : playerRepoList) {
            playerList.add(playerRepo.getLeaderboardPlayer());
        }
        return playerList;
    }
}
