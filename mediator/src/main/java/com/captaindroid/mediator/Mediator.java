package com.captaindroid.mediator;

import android.content.Context;
import android.util.Log;

import com.captaindroid.mediator.listener.AdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import org.jetbrains.annotations.NotNull;

public class Mediator {
    private static Mediator m;

    private InterstitialAd interstitialAd;
    private boolean isTesting = false;
    private Context context;

    public static Mediator with(Context context){
        if(m == null){
            m = new Mediator();
        }
        m.context = context;
        AudienceNetworkAds.initialize(context);
        return m;
    }

    public Mediator setTesting(boolean isTesting){
        this.isTesting = isTesting;
        return m;
    }

    public void loadInterstitialAd(String placementId, @NotNull AdListener listener){
        if(isTesting){
            AdSettings.setTestMode(true);
            placementId = "IMG_16_9_LINK#" + placementId;
        }
        interstitialAd = new InterstitialAd(context, placementId);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                listener.onAdError(new com.captaindroid.mediator.Ad(interstitialAd, AdType.META));
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                listener.onAdError(new com.captaindroid.mediator.Ad(interstitialAd, AdType.META, adError.getErrorMessage()));
            }

            @Override
            public void onAdLoaded(Ad ad) {
                listener.onAdLoaded(new com.captaindroid.mediator.Ad(interstitialAd, AdType.META));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                listener.onAdShown(new com.captaindroid.mediator.Ad(interstitialAd, AdType.META));
            }
        };
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }
}
