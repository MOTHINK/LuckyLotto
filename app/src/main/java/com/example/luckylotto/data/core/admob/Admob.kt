package com.example.luckylotto.data.core.admob

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

class Admob {

    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private val rewardedInterstitialAdUnitId = "ca-app-pub-7926597932830881/5357602642"
    companion object {
        val instance:Admob by lazy { Admob() }
    }
    fun initializeMobileAds(context: Context) {
        MobileAds.initialize(context) {}
    }
    fun loadRewardedInterstitialAd(context: Context, obtainReward: () -> Unit) {
        val adRequest = AdRequest.Builder().build()

        RewardedInterstitialAd.load(context,rewardedInterstitialAdUnitId, adRequest, object : RewardedInterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedInterstitialAd) {
                rewardedInterstitialAd = ad
                Log.d("Ad loaded", "RewardedInterstitialAd was loaded.")

                (context as? Activity)?.let { showRewardedInterstitialAd(it) { obtainReward() } }

                rewardedInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdClicked() {
                        Log.d("Ad clicked", "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.d("Ad_dismissed", "Ad was dismissed.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d("Ad showed", "Ad was shown.")
                    }

                }
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("Ad failed to Load", adError.message)
                rewardedInterstitialAd = null
            }
        })
    }

    fun showRewardedInterstitialAd(activity: Activity, onUserEarnedReward: () -> Unit) {
        if(rewardedInterstitialAd != null) {
            rewardedInterstitialAd?.show(activity, OnUserEarnedRewardListener { rewardItem ->
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Log.d("Reward amount", rewardAmount.toString())
                Log.d("Reward type", rewardType)
                onUserEarnedReward()
                rewardedInterstitialAd = null
                Log.d("Reward_earned", "User earned the reward.")
            })
        } else {
            Log.d("Ad not loaded", "The rewarded interstitial ad wasn't ready yet.")
        }
    }
}