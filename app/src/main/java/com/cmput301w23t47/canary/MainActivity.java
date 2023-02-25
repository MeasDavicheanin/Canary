package com.cmput301w23t47.canary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

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

    private ActivityResultLauncher<Object> qrActivityLauncher;
    private HomeFragment homeFragment;
    private LeaderboardFragment leaderboardFragment;

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
        initNavbar();
        if (savedInstanceState == null) {
            navigateToHome();
        }
    }

    /**
     * Initializes the navbar
     */
    private void initNavbar() {
        // configure selected option
        binding.bottomNavigationLayout.bottomNavigation.setSelectedItemId(R.id.page_home);
        // add listener for navbar
        binding.bottomNavigationLayout.bottomNavigation.setOnItemSelectedListener(item -> {
            NavbarController.handleSelection(item, this);
            return true;
        });
    }

    /**
     * Navigates to the leaderboard page
     */
    public void navigateToHome() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, homeFragment, null)
                .commit();
    }

    /**
     * Navigates to the leaderboard page
     */
    @Override
    public void navigateToLeaderboard() {
        if (leaderboardFragment == null) {
            leaderboardFragment = LeaderboardFragment.newInstance();
        }
    }


}