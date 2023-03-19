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

    public static Mediator with(Context context){
        if(m == null){
            m = new Mediator();
        }
        AudienceNetworkAds.initialize(context);
        return m;
    }

    public void loadInterstitialAd(Context context, String placementId, @NotNull AdListener listener){
        if(BuildConfig.DEBUG){
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

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("err", adError.getErrorMessage());
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

            }
        };
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }
}
