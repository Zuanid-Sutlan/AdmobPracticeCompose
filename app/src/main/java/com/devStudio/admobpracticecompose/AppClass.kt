package com.devStudio.admobpracticecompose

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AppClass : Application() {


    private lateinit var appOpenAdManager: AppOpenAdManager

    private val LOG_TAG = "AppClassLogTag"

    override fun onCreate() {
        super.onCreate()

        val backgroundScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

        backgroundScope.launch {
            MobileAds.initialize(this@AppClass) {}
        }

        appOpenAdManager = AppOpenAdManager(this)
        appOpenAdManager.loadAd()

        InterstitialAdManager.init(this)
        InterstitialAdManager.loadAd()

        RewardedAdManager.init(this)
        RewardedAdManager.loadAd()

        NativeAdManager.init(this)

    }



}