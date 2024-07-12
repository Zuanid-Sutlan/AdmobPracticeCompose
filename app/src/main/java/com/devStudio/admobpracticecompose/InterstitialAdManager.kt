package com.devStudio.admobpracticecompose

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialAdManager {

    private lateinit var context : Context
    private var interstitialAd: InterstitialAd? = null


    fun init(context: Context) {
        this.context = context
    }


    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, AdUnitIds.Interstitial.id, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
                loadAd()
            }
        })
    }

    fun showAd(context: Context) {
        interstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Load a new ad after the previous one is dismissed
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    // Handle the error
                    loadAd()
                }

                override fun onAdShowedFullScreenContent() {
                    // Ad is shown, set interstitialAd to null
                    interstitialAd = null
                }
            }
            ad.show(context as Activity)
        } ?: run {
            // If the ad is not ready, load a new ad
            loadAd()
        }
    }
}