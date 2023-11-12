// MainActivity.java
package com.example.gottagopiss;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.animation.Animator;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TxtGGP = findViewById(R.id.GottaGoPiss);
        TxtGGP.setTextSize(50);
        TxtGGP.setTextColor(Color.rgb(239, 204, 0));


        Button scanButton = findViewById(R.id.scanButton);
        scanButton.setTextSize(30);
        scanButton.setBackgroundColor(Color.rgb(239, 204, 0));
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Add animation
                Animator scale = ObjectAnimator.ofPropertyValuesHolder(v,
                        PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 1.1f, 1),
                        PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 1.1f, 1)
                );
                scale.setDuration(200);
                scale.start();

                // Start QR code scanning
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan a QR code");
                integrator.setCameraId(0);

                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);


                integrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // Get the scanned content
                String scannedContent = result.getContents();

                // Open LeaderboardActivity and pass the scanned content as an extra
                Intent intent = new Intent(this, LeaderboardActivity.class);
                intent.putExtra("SCANNED_CONTENT", scannedContent);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
