package com.cmput301w23t47.canary.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.LeaderboardPlayer;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Adapter for Leaderboard rank list
 */
public class LeaderboardRankListAdapter extends ArrayAdapter<LeaderboardPlayer> {
    private ArrayList<LeaderboardPlayer> playersList;

    /**
     * Constructor
     * @param players the ArrayList used by adapter
     */
    public LeaderboardRankListAdapter(Context context, ArrayList<LeaderboardPlayer> players) {
        super(context, 0, players);
        this.playersList = players;
    }

    /**
     * Gets the view to be rendered by adapter
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(super.getContext()).
                    inflate(R.layout.content_leaderboard_rank_list_item, parent, false);
        } else {
            view = convertView;
        }
        LeaderboardPlayer player = getItem(position);
        TextView rankView = view.findViewById(R.id.player_list_rank);
        TextView usernameView = view.findViewById(R.id.player_username);
        TextView scoreView = view.findViewById(R.id.player_score);

        rankView.setText(String.format(Locale.CANADA, "%d", position+1));
        usernameView.setText(player.getUsername());
        scoreView.setText(String.format(Locale.CANADA, "%d Pt", player.getScore()));
        return view;
    }

    /**
     * Getter for the ArrayList used in Ranking
     * @return ArrayList used by adapter
     */
    public ArrayList<LeaderboardPlayer> getPlayersList() {
        return playersList;
    }
}
