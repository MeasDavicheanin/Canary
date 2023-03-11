package com.cmput301w23t47.canary.view.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayerSearchAdapter extends RecyclerView.Adapter<PlayerSearchAdapter.ViewHolder>{
    private ArrayList<Player> players;
    //constructor
    public PlayerSearchAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    public void filterList(ArrayList<Player> filteredList) {
        players = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerSearchAdapter.ViewHolder holder, int position) {
        Player player = players.get(position);
        holder.playerName.setText(player.getUsername());
        holder.playerScore.setText(String.format(Locale.CANADA, "%d", player.getScore()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setFilterList(List<Player> filteredModelList) {
        players = new ArrayList<>();
        players.addAll(filteredModelList);
        notifyDataSetChanged();
    }

    /**
     * Updates the list to display the new list of items
     * @param newPlayerList the new list of players
     */
    public void updateList(ArrayList<Player> newPlayerList) {
        players.clear();
        players.addAll(newPlayerList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView playerName;
        private final TextView playerScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.player_list_username);
            playerScore=itemView.findViewById(R.id.player_list_score);
        }
    }
}
