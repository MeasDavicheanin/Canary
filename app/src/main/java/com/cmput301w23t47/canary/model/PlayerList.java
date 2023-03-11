package com.cmput301w23t47.canary.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerList implements Serializable {
    private ArrayList<Player> players;
    public ArrayList<Player> getPlayers() {
        //get from firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //get from collection players
        db.collection("players").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (Player player : task.getResult().toObjects(Player.class)) {
                    players.add(player);
                }
            }
        });
        return players;
    }
}
