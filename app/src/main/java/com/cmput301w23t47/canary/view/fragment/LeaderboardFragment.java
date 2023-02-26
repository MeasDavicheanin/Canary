package com.cmput301w23t47.canary.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.callback.UpdateLeaderboardCallback;
import com.cmput301w23t47.canary.databinding.FragmentLeaderboardBinding;
import com.cmput301w23t47.canary.model.Leaderboard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment implements
        UpdateLeaderboardCallback {
    public static final String TAG = "LeaderboardFragment";
    private static final String progressBarTitle = "Loading Leaderboards";
    private static final String progressBarMessage = "Should take only a moment...";

    private Leaderboard leaderboard;

    private FragmentLeaderboardBinding binding;
    private ProgressDialog progressDialog;

    public LeaderboardFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        return fragment;
    }

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
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && leaderboard == null) {
            initProgressBar();
        }
    }

    private void initProgressBar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle(progressBarTitle);
        progressDialog.setMessage(progressBarMessage);
        progressDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void updatePlayer(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        progressDialog.dismiss();
        fillLeaderboardInfo();
    }

    private void fillLeaderboardInfo() {

    }
}