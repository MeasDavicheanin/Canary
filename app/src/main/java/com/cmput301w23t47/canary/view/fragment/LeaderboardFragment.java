package com.cmput301w23t47.canary.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.callback.UpdateLeaderboardCallback;
import com.cmput301w23t47.canary.controller.FirestoreController;
import com.cmput301w23t47.canary.controller.LeaderboardController;
import com.cmput301w23t47.canary.databinding.FragmentLeaderboardBinding;
import com.cmput301w23t47.canary.model.Leaderboard;
import com.cmput301w23t47.canary.model.LeaderboardPlayer;
import com.cmput301w23t47.canary.view.adapter.LeaderboardRankListAdapter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Fragment for the leaderboard
 */
public class LeaderboardFragment extends Fragment implements
        UpdateLeaderboardCallback {
    public static final String TAG = "LeaderboardFragment";
    public static final String title = "Leaderboard";
    private static final String progressBarTitle = "Loading Leaderboards";
    private static final String progressBarMessage = "Should take only a moment...";

    private Leaderboard leaderboard;
    private String playerUsername = "";

    private FragmentLeaderboardBinding binding;
    private FirestoreController firestoreController;
    private LeaderboardRankListAdapter leaderboardRankListAdapter;

    public LeaderboardFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        firestoreController = new FirestoreController();
        firestoreController.getLeaderboard(this);
        showLoadingBar();
        leaderboardRankListAdapter = new LeaderboardRankListAdapter(getContext(), new ArrayList<>());
        binding.rankingList.setAdapter(leaderboardRankListAdapter);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && leaderboard == null) {
            firestoreController.getLeaderboard(this);
            showLoadingBar();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void updateLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        fillLeaderboardInfo();
    }

    private void fillLeaderboardInfo() {
        // set Global Leaderboard val
        binding.scoreLeaderboardVal.setText(String.format(Locale.CANADA, "%s: %d Pt",
                leaderboard.getMaxScorePlayer(), leaderboard.getMaxScore()));
        binding.qrScannedLeaderboardVal.setText(String.format(Locale.CANADA, "%s: %d QR",
                leaderboard.getMaxQrPlayer(), leaderboard.getMaxQr()));

        // set personal ranking
        int playerScoreRank = LeaderboardController.getRankForPlayer(playerUsername, leaderboard.getByScore());
        int playerMaxQrRank = LeaderboardController.getRankForPlayer(playerUsername, leaderboard.getByHighestScoringQr());
        binding.scoreRankVal.setText(String.format(Locale.CANADA, "%d Out of %d",
                playerScoreRank, leaderboard.getByScore().size()));
        binding.highestScoringQrRankVal.setText(String.format(Locale.CANADA, "%d Out of %d",
                playerMaxQrRank, leaderboard.getByHighestScoringQr().size()));

        // update ranking list
        ArrayList<LeaderboardPlayer> rankPlayers = leaderboardRankListAdapter.getPlayersList();
        rankPlayers.clear();
        rankPlayers.addAll(leaderboard.getByScore());
        leaderboardRankListAdapter.notifyDataSetChanged();
        hideLoadingBar();
    }

    /**
     * Shows the loading bar
     */
    private void showLoadingBar() {
        binding.progressBarLayout.progressBarBox.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the loading bar
     */
    private void hideLoadingBar() {
        binding.progressBarLayout.progressBarBox.setVisibility(View.GONE);
    }
}