package com.example.gottagopiss;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_activity);

        // Retrieve the scanned content from the intent
        String scannedContent = getIntent().getStringExtra("SCANNED_CONTENT");

        // Get the RatingBar and change its color
        RatingBar ratingBar = findViewById(R.id.stars);

        int highlightedStars = 7;

        // Set the progress of the RatingBar to the number of highlighted stars
        ratingBar.setProgress(highlightedStars);

        Button peeQ = findViewById(R.id.peeQuality);
        TextView score = findViewById(R.id.score);
        Button res = findViewById(R.id.resultat);

        peeQ.setTextSize(30);
        peeQ.setBackgroundColor(Color.rgb(239, 204, 0));
        score.setTextSize(30);
        score.setTextColor(Color.rgb(239, 204, 0));
        res.setText(scannedContent);
        res.setBackgroundColor(Color.rgb(239, 204, 0));
        res.setTextSize(40);

    }
}



