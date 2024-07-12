package com.devStudio.admobpracticecompose

sealed class AdUnitIds(val id: String) {

    data object Banner : AdUnitIds("ca-app-pub-3940256099942544/9214589741")

    data object AppOpen : AdUnitIds("ca-app-pub-3940256099942544/9257395921")

    data object Interstitial : AdUnitIds("ca-app-pub-3940256099942544/1033173712")

    data object Rewarded : AdUnitIds("ca-app-pub-3940256099942544/5224354917")

    data object Native : AdUnitIds("ca-app-pub-3940256099942544/2247696110")


}