package com.captaindroid.mediator.listener;

import com.captaindroid.mediator.Ad;

public abstract class AdEvents {
    public void onAdShown(Ad ad){};
    public void onAdError(Ad ad){};
    public void onAdClosed(Ad ad){};
    public void onAdLoadFailed(Ad ad){};
    public void onAdLoaded(Ad ad){};
    public void onAdClicked(Ad ad){};
    public void onAdImpression(Ad ad){};
}
