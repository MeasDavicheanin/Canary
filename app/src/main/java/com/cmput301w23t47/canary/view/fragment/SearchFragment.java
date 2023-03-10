package com.cmput301w23t47.canary.view.fragment;


import android.app.appsearch.SearchResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.Player;
import com.cmput301w23t47.canary.view.adapter.PlayerSeachAdapter;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment{
    public static final String TAG ="SearchFragment" ;
    private RecyclerView searchResult;

    private PlayerSeachAdapter playerSeachAdapter;

    private ArrayList<Player> playersArrayList;
    public boolean onQueryTextChange(String newText) {
        final List<Player> filteredModelList = filter(playersArrayList, newText);
        playerSeachAdapter.setFilterList(filteredModelList);
        return true;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private List<Player>filter(List<Player> models, String query) {
        query = query.toLowerCase();
        final List<Player> filteredModelList = new ArrayList<>();
        for (Player model : models) {
            final String text = model.getUsername().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
