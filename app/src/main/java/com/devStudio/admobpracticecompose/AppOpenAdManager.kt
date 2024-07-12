package com.devStudio.admobpracticecompose

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdManager(private val application: Application) : Application.ActivityLifecycleCallbacks {
    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    private var isShowingAd = false
    private var currentActivity: Activity? = null

    private var isAvailable = false

    val TAG = "AppOpenAdManager"

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun loadAd() {
        if (appOpenAd == null) {
            val request = AdRequest.Builder().build()
            AppOpenAd.load(
                application, AdUnitIds.AppOpen.id, request,
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        appOpenAd = ad
                        isAvailable = true
//                        isShowingAd = true
//                        showAdIfAvailable()
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        // Handle the error
                        isAvailable = false
                        loadAd()
                    }
                }
            )
        }
    }


    fun showAdIfAvailable() {
        if (isShowingAd || appOpenAd == null) {
            return
        }

        appOpenAd?.show(currentActivity!!)
        isShowingAd = true

        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                isAvailable = false
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                isShowingAd = false
                isAvailable = false
                loadAd()
            }

            override fun onAdShowedFullScreenContent() {
                isShowingAd = true
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, "onActivityResumed: Triggered")
        currentActivity = activity
        showAdIfAvailable()
    }

    // Implement other lifecycle callbacks if needed

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, "onActivityStarted: Triggered")
//       if (!isShowingAd){
//           currentActivity = activity
//           showAdIfAvailable()
//       }
    }
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated: Triggered")
        /*if (isAvailable){
            currentActivity = activity
            showAdIfAvailable()
        }*/
    }
}
