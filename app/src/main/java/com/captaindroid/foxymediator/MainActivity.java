package com.captaindroid.foxymediator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.captaindroid.mediator.Ad;
import com.captaindroid.mediator.Mediator;
import com.captaindroid.mediator.R;
import com.captaindroid.mediator.listener.AdListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mediator.with(this).loadInterstitialAd(this, "5476461102469724_5478329148949586", new AdListener() {
            @Override
            public void onAdShown(Ad ad) {
                Log.e("show", "from here");
            }

            @Override
            public void onAdError(Ad ad) {

            }

            @Override
            public void onAdClosed(Ad ad) {

            }

            @Override
            public void onAdLoadFailed(Ad ad) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                ad.show();
            }
        });
    }
}