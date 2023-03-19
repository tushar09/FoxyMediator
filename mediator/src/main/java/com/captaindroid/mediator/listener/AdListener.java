package com.captaindroid.mediator.listener;

import com.captaindroid.mediator.Ad;

public interface AdListener {
    void onAdShown(Ad ad);
    void onAdError(Ad ad);
    void onAdClosed(Ad ad);
    void onAdLoadFailed(Ad ad);
    void onAdLoaded(Ad ad);
}
