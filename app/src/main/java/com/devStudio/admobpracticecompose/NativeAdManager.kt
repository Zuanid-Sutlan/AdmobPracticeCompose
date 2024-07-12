package com.devStudio.admobpracticecompose

import android.content.Context
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions

object NativeAdManager {

    private lateinit var context : Context
    var nativeAd: NativeAd? = null

    fun init(context: Context) {
        this.context = context
        loadNativeAd()
    }

    fun isNativeAdLoaded(): Boolean {
        return nativeAd != null
    }

    private fun loadNativeAd() {
        val adLoader = AdLoader.Builder(context, AdUnitIds.Native.id)
            .forNativeAd { ad: NativeAd ->
                nativeAd = ad
                // Update your UI with the ad content
//                setContent { MyApp(nativeAd) }
            }
            .withAdListener(object : com.google.android.gms.ads.AdListener() {
                override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                    // Handle the error
                    loadNativeAd()
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }
}