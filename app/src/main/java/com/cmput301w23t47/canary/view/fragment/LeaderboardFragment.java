package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.databinding.FragmentLeaderboardBinding;
import com.cmput301w23t47.canary.model.Leaderboard;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {
    private Leaderboard leaderboard;
    private FragmentLeaderboardBinding binding;

    public LeaderboardFragment() {}

    public LeaderboardFragment(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param leaderboard the leaderboard object.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(Leaderboard leaderboard) {
        LeaderboardFragment fragment = new LeaderboardFragment(leaderboard);
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
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}