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

public class PlayerSeachAdapter extends RecyclerView.Adapter<PlayerSeachAdapter.ViewHolder>{
    private ArrayList<Player> players;
    //constructor
    public PlayerSeachAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    public void fillterList(ArrayList<Player> filteredList) {
        players = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PlayerSeachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerSeachAdapter.ViewHolder holder, int position) {
        Player player = players.get(position);
        holder.playerName.setText(player.getUsername());
        long score = player.getScore();
        holder.playerScore.setText( Long.toString(score));
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView playerName;
        private final TextView playerScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.seachUserName);
            playerScore=itemView.findViewById(R.id.searchUserScore);
        }
    }
}
