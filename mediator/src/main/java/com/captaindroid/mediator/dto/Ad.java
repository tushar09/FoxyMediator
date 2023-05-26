package com.captaindroid.mediator.dto;

import com.captaindroid.mediator.enums.AdNetwork;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.NativeAd;

public class Ad {
    private AdNetwork adNetwork;
    private InterstitialAd interstitialAd;
    private NativeAd nativeAd;

    private String adError;

    public Ad(InterstitialAd interstitialAd, AdNetwork adNetwork) {
        this.adNetwork = adNetwork;
        this.interstitialAd = interstitialAd;
    }

    public Ad(NativeAd nativeAd, AdNetwork adNetwork) {
        this.adNetwork = adNetwork;
        this.nativeAd = nativeAd;
    }

    public Ad(InterstitialAd interstitialAd, AdNetwork adNetwork, String adError) {
        this.adNetwork = adNetwork;
        this.interstitialAd = interstitialAd;
        this.adError = adError;
    }

    public Ad(NativeAd nativeAd, AdNetwork adNetwork, String adError) {
        this.adNetwork = adNetwork;
        this.nativeAd = nativeAd;
        this.adError = adError;
    }

    public void show(){
        if(adNetwork == AdNetwork.META){
            interstitialAd.show();
        }else if(adNetwork == AdNetwork.ADMOB){

        }
    }

    public AdNetwork getAdType() {
        return adNetwork;
    }

    public String getAdError() {
        return adError;
    }
}
