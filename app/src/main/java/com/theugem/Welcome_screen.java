package com.theugem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

public class Welcome_screen extends AppCompatActivity {

    ImageView start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        TextView privacy = (TextView) findViewById(R.id.privacy);
        privacy.setMovementMethod(LinkMovementMethod.getInstance());

        ImageButton exit = (ImageButton) findViewById(R.id.stop);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Welcome_screen.this);
                builder.setMessage("You really want to Exit?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        start = (ImageView) findViewById(R.id.start);
        start.setOnClickListener(v -> openMainActivity());

//        AudienceNetworkAds.initialize(this);
        LinearLayout adViewContainer1 = findViewById(R.id.banner_container1);
        LinearLayout adViewContainer2 = findViewById(R.id.banner_container2);
//        AdView fbAdBanner = AdsManager.createFacebookBannarAd(this);
//        adViewContainer.addView(fbAdBanner);
        AdsManager.initialise(this);
        com.google.android.gms.ads.AdView mAdView = new AdView(this);
        adViewContainer1.addView(mAdView);
        AdsManager.showBannerAd(mAdView, this);

        com.google.android.gms.ads.AdView mAdView2 = new AdView(this);
        adViewContainer2.addView(mAdView2);
        AdsManager.showBannerAd(mAdView2, this);

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}