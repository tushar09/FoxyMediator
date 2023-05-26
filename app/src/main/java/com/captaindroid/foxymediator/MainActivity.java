package com.captaindroid.foxymediator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.captaindroid.mediator.dto.Ad;
import com.captaindroid.mediator.Mediator;
import com.captaindroid.mediator.R;
import com.captaindroid.mediator.listener.AdEvents;
import com.captaindroid.mediator.listener.NativeAdEvents;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mediator.with(this).setTesting(true).loadInterstitialAd("5476461102469724_5478329148949586", new AdEvents() {
            @Override
            public void onAdShown(Ad ad) {
                Log.e("get url", "sho");
            }

            @Override
            public void onAdError(Ad ad) {
                Log.e("get url", "err");
            }

            @Override
            public void onAdClosed(Ad ad) {
                Log.e("get url", "cl");
            }

            @Override
            public void onAdLoadFailed(Ad ad) {
                Log.e("get url", "lo");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                ad.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                super.onAdClicked(ad);
                Log.e("get url", "cl");
            }

            @Override
            public void onAdImpression(Ad ad) {
                super.onAdImpression(ad);
                Log.e("get url", "im");
            }
        });


        Mediator.with(this).setTesting(true).loadFullScreenNativeAd("5476461102469724_6256472664468560", new NativeAdEvents() {
            @Override
            public void onAdLoaded(Ad ad, View view) {
                super.onAdLoaded(ad, view);
                ((LinearLayout)findViewById(R.id.ll)).addView(view);
                Log.e("ads", view != null ? "no null": "null");
            }

            @Override
            public void onAdImpression(Ad ad) {
                super.onAdImpression(ad);
                Log.e("ads", "impressed");
            }
        });
    }
}