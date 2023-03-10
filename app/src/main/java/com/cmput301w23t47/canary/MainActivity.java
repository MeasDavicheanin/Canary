package com.cmput301w23t47.canary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cmput301w23t47.canary.callback.UpdatePlayerCallback;
import com.cmput301w23t47.canary.controller.NavbarController;
import com.cmput301w23t47.canary.controller.QrCodeUtil;
import com.cmput301w23t47.canary.databinding.ActivityMainBinding;
import com.cmput301w23t47.canary.model.Leaderboard;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.view.contract.QrCodeContract;
import com.cmput301w23t47.canary.view.fragment.HomeFragment;
import com.cmput301w23t47.canary.view.fragment.LeaderboardFragment;


/**
 * Main Acitvity
 * @author Meharpreet Singh Nanda
 */
public class MainActivity extends AppCompatActivity implements
//        UpdatePlayerCallback,
        NavbarController.NavigateToPage {

    private ActivityMainBinding binding;
    int i = 0;


    private HomeFragment homeFragment;
    private LeaderboardFragment leaderboardFragment;
    private Fragment activeFragment;

    // TODO: Get the actual username
    private String playerUsername = "jamesk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(savedInstanceState);
    }

    /**
     * Initialization for activity
     */
    private void init(Bundle savedInstanceState) {
        initFragmentStack();
        initNavbar();
    }

    /**
     * Initializes the fragment stack
     */
    private void initFragmentStack() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            leaderboardFragment = new LeaderboardFragment(playerUsername);
        }
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, homeFragment, HomeFragment.TAG)
                .add(R.id.fragment_container_view, leaderboardFragment, LeaderboardFragment.TAG)
                .hide(leaderboardFragment)
                .commit();
        activeFragment = homeFragment;
    }

    /**
     * Initializes the navbar
     */
    private void initNavbar() {
        // add listener for navbar
        binding.bottomNavigationLayout.bottomNavigation.setOnItemSelectedListener(item -> {
            NavbarController.handleSelection(item, this);
            return true;
        });
        // ignore if option reselected
        binding.bottomNavigationLayout.bottomNavigation.setOnItemReselectedListener(item -> {});
        // configure selected option; calls the itemSelectedCallback function
        binding.bottomNavigationLayout.bottomNavigation.setSelectedItemId(R.id.page_home);
    }

    /**
     * Navigates to the leaderboard page
     */
    @Override
    public void navigateToHome() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(activeFragment)
                .show(homeFragment)
                .commit();
        activeFragment = homeFragment;
    }


    @Override
    public void navigateToSearch() {

    }

    /**
     * Navigates to the leaderboard page
     */
    @Override
    public void navigateToLeaderboard() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(activeFragment)
                .show(leaderboardFragment)
                .commit();
        activeFragment = leaderboardFragment;
    }

    @Override
    public void navigateToPlayers() {

    }

    @Override
    public void navigateToProfile() {

    }


}