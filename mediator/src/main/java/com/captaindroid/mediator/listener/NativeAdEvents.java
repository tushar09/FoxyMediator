package com.captaindroid.mediator.listener;

import android.view.View;

import com.captaindroid.mediator.dto.Ad;

public abstract class NativeAdEvents extends AdEvents{
    public void onAdLoaded(Ad ad, View view){};
}
