package com.theugem;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import androidx.annotation.NonNull;

public class AdsManager {

    private static final String BANNER_FB_ID = "298345895679236_298346219012537";
    private static final String INTERSTITIAL_FACEBOOK_AD = "298345895679236_298346332345859";

    private static final String BANNER_ADMOB_ID = "ca-app-pub-4143387699699889/6871737944";
    private static final String INTERSTITIAL_ADMOB_AD = "ca-app-pub-4143387699699889/2932492931";

    public static com.facebook.ads.AdView createFacebookBannarAd(Context context) {
        com.facebook.ads.AdView fbAdview = new com.facebook.ads.AdView(context, BANNER_FB_ID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        fbAdview.loadAd();
        return fbAdview;
    }

    public static void fbInterstitialAds(Context context) {

        com.facebook.ads.InterstitialAd interstitialAdFacebook = new com.facebook.ads.InterstitialAd(context, INTERSTITIAL_FACEBOOK_AD);
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {

            @Override
            public void onError(Ad ad, AdError adError) {
//                Toast.makeText(context, "Fb Ads have some error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAdFacebook.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
            }
        };

        interstitialAdFacebook.loadAd(
                interstitialAdFacebook.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public static void initialise(Context context) {
        MobileAds.initialize(context, initializationStatus -> {

        });

    }

    public static void showBannerAd(AdView adView, Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(BANNER_ADMOB_ID);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.loadAd(adRequest);
    }

    public static void showInterstitialAd(Activity context, AdListener adListener) {

//        if (!AudioMarketplaceRepository.isInTrialMode(context.getApplicationContext()) && !AudioMarketplaceRepository.isPremiumPurchased(context)) {
        InterstitialAd.load(context, INTERSTITIAL_ADMOB_AD, new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        adListener.onAdLoaded();
                        interstitialAd.show(context);

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }


                });

//        }
    }

}

