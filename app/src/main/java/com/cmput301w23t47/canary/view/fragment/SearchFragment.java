package com.cmput301w23t47.canary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.Leaderboard;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private static final String progressBarTitle = "Loading search results";
    private static final String progressBarMessage = "Should take only a moment...";
    private Leaderboard leaderboard;
    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
