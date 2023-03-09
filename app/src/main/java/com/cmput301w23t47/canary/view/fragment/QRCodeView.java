package com.cmput301w23t47.canary.view.fragment;

import static com.cmput301w23t47.canary.view.fragment.LeaderboardFragment.TAG;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.model.PlayerQrCode;
import com.cmput301w23t47.canary.model.Snapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class QRCodeView extends Fragment {

    private PlayerQrCode currentPlayerQRCode;
    private TextView currentQRCodeName;
    private TextView currentQRCodeScore;
    private TextView currentQRCodeScanDateTime;
    private TextView currentQRCodeScanLocation;
    private ImageView currentQRCodeImage;
    ListView commentsListView;

    public QRCodeView(PlayerQrCode currentPlayerQRCode) {
        this.currentPlayerQRCode = currentPlayerQRCode;
    }

    public QRCodeView() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void updateFragmentData(){
        if (currentPlayerQRCode == null) {
            return;
        }
        String name = currentPlayerQRCode.getName();
        Location location = currentPlayerQRCode.getLocation();
        Snapshot qrCodeImage = currentPlayerQRCode.getSnapshot();
        long score = currentPlayerQRCode.getQrCode().getScore();
        // **Need to set currentQRCodeScanLocation, currentQRCodeImage, currentQRCodeScanDateTime**
        // we don't have any scan date time for the player qr codes yet
        if(currentPlayerQRCode != null){
            if(name != null)
                currentQRCodeName.setText(name);
            if(score >= 0)
                currentQRCodeScore.setText("Score "+ String.valueOf(score)+ " Pts");
            if(qrCodeImage != null){
                // fill this once snapshot class is complete
            }
            if(location != null){
                //fill this once location class is complete
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code_view, container, false);
//        currentPlayerQRCode = getArguments().getParcelable("playerQRCode");
        currentQRCodeName = view.findViewById(R.id.QRCodeName);
        currentQRCodeScore = view.findViewById(R.id.QRCodeScore);
        currentQRCodeScanLocation = view.findViewById(R.id.QRCardLocation);
        currentQRCodeScanDateTime = view.findViewById(R.id.QRCodeScanDateTime);
        currentQRCodeImage = view.findViewById(R.id.QRCodeImage);
        updateFragmentData();
        getImage(getString(R.string.random_image_generator));
        return view;
    }

    public void getImage(String url) {
        //extracting json data
        //RequestQueue queue = Volley.newRequestQueue(this);
        Log.d(TAG, "getImage: " + url);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d(TAG, "onResponse: " + response.toString());
                try {
                    JSONObject data = response.getJSONObject(0);
                    String url = data.getString("url");
                    Log.d(TAG, "onResponse" + data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(arrayRequest);
    }
}