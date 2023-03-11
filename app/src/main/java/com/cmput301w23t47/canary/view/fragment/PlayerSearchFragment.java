package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.callback.GetPlayerListCallback;
import com.cmput301w23t47.canary.controller.FirestorePlayerController;
import com.cmput301w23t47.canary.databinding.FragmentPlayerSearchBinding;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.view.adapter.PlayerSearchAdapter;

import java.util.ArrayList;

/**
 * The fragment for searching the player
 */
public class PlayerSearchFragment extends Fragment implements GetPlayerListCallback {
    public PlayerSearchFragment() {
    }

    FragmentPlayerSearchBinding binding;

    FirestorePlayerController firestorePlayerController = new FirestorePlayerController();
    PlayerSearchAdapter searchAdapter;
    ArrayList<Player> players = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerSearchBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    /**
     * Initializes the view
     */
    private void init() {
        searchAdapter = new PlayerSearchAdapter(players);
        binding.searchResultList.setAdapter(searchAdapter);
        binding.searchResultList.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.searchResultList.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_shape));
        binding.searchResultList.addItemDecoration(dividerItemDecoration);
        firestorePlayerController.getListOfPlayers(this);
        showLoadingBar();
    }

        @Override
    public void onHiddenChanged(boolean hidden){
        if(!hidden && players == null){
            firestorePlayerController.getListOfPlayers(this);
            showLoadingBar();
        }
    }

    /**
     * Gets the players list when they are available
     * @param players the list of players
     */
    @Override
    public void getPlayerList(ArrayList<Player> players) {
        this.players = players;
        updatePlayersList();
    }

    /**
     * Update the view of the players list
     */
    private void updatePlayersList() {
        if (this.players == null) {
            return;
        }
        searchAdapter.updateList(players);
        hideLoadingBar();
    }

    /**
     * Shows the loading bar
     */
    private void showLoadingBar() {
        binding.progressBarBox.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the loading bar
     */
    private void hideLoadingBar() {
        binding.progressBarBox.setVisibility(View.GONE);
    }
}