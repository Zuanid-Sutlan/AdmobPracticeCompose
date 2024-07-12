package com.devStudio.admobpracticecompose

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.devStudio.admobpracticecompose.ui.theme.AdmobPracticeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current

            AdmobPracticeComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray)
                    ) {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )


                        if (NativeAdManager.isNativeAdLoaded()) {
                            NativeAdView(Modifier.align(Alignment.TopCenter),ad = NativeAdManager.nativeAd)
                        }

                        Column(
                            modifier = Modifier
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {



                            Button(
                                onClick = {
                                    InterstitialAdManager.showAd(context)
                                }
                            ) {
                                Text(text = "Show Interstitial Ad")
                            }

                            Button(
                                onClick = {
                                    RewardedAdManager.showAd(context){
                                        // Handle the reward
                                        Toast.makeText(
                                            context,
                                            "You Got the Reward",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            ) {
                                Text(text = "Show Rewarded Ad")
                            }

                            Button(
                                onClick = {
                                    context.startActivity(Intent(context, MainActivity2::class.java))
                                }
                            ) {
                                Text(text = "Go to 2nd Activity")
                            }

                            Button(
                                onClick = {
                                    context.startActivity(Intent(context, MainActivity3::class.java))
                                }
                            ) {
                                Text(text = "Go to 3rd Activity")
                            }

                        }


                        BannerAdView(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .navigationBarsPadding(),
                            adId = AdUnitIds.Banner.id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdmobPracticeComposeTheme {
        Greeting("Android")
    }
}