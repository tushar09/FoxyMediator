package com.captaindroid.mediator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.captaindroid.mediator.databinding.LayoutFullScreenNativeAdsBinding;
import com.captaindroid.mediator.databinding.NativeAdsLayoutHolderBinding;
import com.captaindroid.mediator.enums.AdNetwork;
import com.captaindroid.mediator.listener.AdEvents;
import com.captaindroid.mediator.listener.NativeAdEvents;
import com.captaindroid.mediator.utils.Constants;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Mediator {
    private static Mediator m;

    private InterstitialAd interstitialAd;
    private boolean isTesting = false;
    private Context context;

    public static Mediator with(Context context) {
        if (m == null) {
            m = new Mediator();
        }
        m.context = context;
        AudienceNetworkAds.initialize(context);
        return m;
    }

    public Mediator setTesting(boolean isTesting) {
        this.isTesting = isTesting;
        return m;
    }

    public void loadInterstitialAd(String placementId, @NotNull AdEvents listener) {
        if (isTesting) {
            AdSettings.setTestMode(true);
            placementId = "IMG_16_9_LINK#" + placementId;
        }
        interstitialAd = new InterstitialAd(context, placementId);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                listener.onAdShown(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META));
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                listener.onAdClosed(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META));
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                listener.onAdError(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META, adError.getErrorMessage()));
            }

            @Override
            public void onAdLoaded(Ad ad) {
                listener.onAdLoaded(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META));
            }

            @Override
            public void onAdClicked(Ad ad) {
                listener.onAdClicked(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META));
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                listener.onAdImpression(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META));
            }
        };
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }

    public void loadFullScreenNativeAd(String placementId, @NotNull NativeAdEvents listener) {
        if (isTesting) {
            AdSettings.setTestMode(true);
            placementId = "IMG_16_9_LINK#" + placementId;
        }

        NativeAdsLayoutHolderBinding binding = NativeAdsLayoutHolderBinding.inflate(LayoutInflater.from(context));
        NativeAd nativeAd = new NativeAd(context, placementId);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                listener.onAdError(new com.captaindroid.mediator.dto.Ad(nativeAd, AdNetwork.META, adError.getErrorMessage()));
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd, context, binding, listener);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                listener.onAdImpression(new com.captaindroid.mediator.dto.Ad(nativeAd, AdNetwork.META));
            }
        };

        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build()
        );

    }

    private void inflateAd(NativeAd nativeAd, Context context, NativeAdsLayoutHolderBinding binding, NativeAdEvents listener) {

        LayoutFullScreenNativeAdsBinding layoutFullScreenNativeAdsBinding = LayoutFullScreenNativeAdsBinding.inflate(LayoutInflater.from(context));

        nativeAd.unregisterView();

        binding.nativeAdContainer.addView(layoutFullScreenNativeAdsBinding.getRoot());

        AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, binding.nativeAdContainer);
        layoutFullScreenNativeAdsBinding.adChoicesContainer.removeAllViews();
        layoutFullScreenNativeAdsBinding.adChoicesContainer.addView(adOptionsView, 0);

        layoutFullScreenNativeAdsBinding.nativeAdTitle.setText(nativeAd.getAdvertiserName());
        layoutFullScreenNativeAdsBinding.nativeAdBody.setText(nativeAd.getAdBodyText());
        layoutFullScreenNativeAdsBinding.nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        layoutFullScreenNativeAdsBinding.nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        layoutFullScreenNativeAdsBinding.nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        layoutFullScreenNativeAdsBinding.nativeAdSponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(layoutFullScreenNativeAdsBinding.nativeAdTitle);
        clickableViews.add(layoutFullScreenNativeAdsBinding.nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(layoutFullScreenNativeAdsBinding.getRoot(), layoutFullScreenNativeAdsBinding.nativeAdMedia, layoutFullScreenNativeAdsBinding.nativeAdIcon, clickableViews);
        listener.onAdLoaded(new com.captaindroid.mediator.dto.Ad(interstitialAd, AdNetwork.META), binding.getRoot());
    }
}
