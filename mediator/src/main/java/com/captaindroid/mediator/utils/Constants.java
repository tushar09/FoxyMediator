package com.captaindroid.mediator.utils;

import com.captaindroid.mediator.dto.Ad;
import com.captaindroid.mediator.enums.AdNetwork;
import com.captaindroid.mediator.listener.AdEvents;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.NativeAd;

public class Constants {
    public static void sendAdShown(AdEvents listener, InterstitialAd interstitialAd){
        listener.onAdShown(new Ad(interstitialAd, AdNetwork.META));
    }

    public static void sendAdClosed(AdEvents listener, InterstitialAd interstitialAd){
        listener.onAdClosed(new Ad(interstitialAd, AdNetwork.META));
    }

    public static void sendAdError(AdEvents listener, InterstitialAd interstitialAd){
        listener.onAdError(new Ad(interstitialAd, AdNetwork.META));
    }

    public static void sendAdError(AdEvents listener, NativeAd nativeAd){
        listener.onAdError(new Ad(nativeAd, AdNetwork.META));
    }

    public static void sendAdLoaded(AdEvents listener, InterstitialAd interstitialAd){
        listener.onAdLoaded(new Ad(interstitialAd, AdNetwork.META));
    }

    public static void sendAdImpression(AdEvents listener, InterstitialAd interstitialAd){
        listener.onAdLoaded(new Ad(interstitialAd, AdNetwork.META));
    }
}
