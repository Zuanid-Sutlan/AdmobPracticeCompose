package com.devStudio.admobpracticecompose

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object RewardedAdManager {

    private lateinit var context: Context
    private var rewardedAd: RewardedAd? = null

    fun init(context: Context) {
        this.context = context
    }

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, AdUnitIds.Rewarded.id, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
            }
        })
    }

    fun showAd(context: Context, onUserEarnedReward: (RewardItem) -> Unit) {
        rewardedAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Load a new ad after the previous one is dismissed
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    // Handle the error
                }

                override fun onAdShowedFullScreenContent() {
                    // Ad is shown, set rewardedAd to null
                    rewardedAd = null
                }
            }
            ad.show(context as Activity) { rewardItem ->
                onUserEarnedReward(rewardItem)
            }

        } ?: run {
            // If the ad is not ready, load a new ad
            loadAd()
        }
    }
}