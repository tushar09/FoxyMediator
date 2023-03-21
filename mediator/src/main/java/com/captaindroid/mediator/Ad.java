package com.captaindroid.mediator;

import com.captaindroid.mediator.listener.AdListener;
import com.facebook.ads.InterstitialAd;

public class Ad {
    private AdType adType;
    private InterstitialAd interstitialAd;

    private String adError;

    public Ad(InterstitialAd interstitialAd, AdType adType) {
        this.adType = adType;
        this.interstitialAd = interstitialAd;
    }

    public Ad(InterstitialAd interstitialAd, AdType adType, String adError) {
        this.adType = adType;
        this.interstitialAd = interstitialAd;
        this.adError = adError;
    }

    public void show(){
        if(adType == AdType.META){
            interstitialAd.show();
        }else if(adType == AdType.ADMOB){

        }
    }

    public AdType getAdType() {
        return adType;
    }
}
