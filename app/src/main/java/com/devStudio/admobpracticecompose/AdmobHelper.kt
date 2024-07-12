package com.devStudio.admobpracticecompose

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.android.gms.ads.nativead.NativeAd


@Composable
fun BannerAdView(
    modifier: Modifier = Modifier,
    adId: String
) {
    val context = LocalContext.current
    var adView by remember { mutableStateOf<AdView?>(null) }

    DisposableEffect(Unit) {
        adView = AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = adId
            loadAd(AdRequest.Builder().build())
        }
        onDispose {
            adView?.destroy()
            adView = null
        }
    }

    adView?.let { mAdView ->
        AndroidView(
            factory = { mAdView },
            modifier = modifier
                .fillMaxWidth()
                .height(AdSize.BANNER.getHeightInPixels(context).dp)
        )
    }
}



@Composable
fun NativeAdView(modifier: Modifier = Modifier, ad: NativeAd?) {
    ad?.let {
        Card(
            modifier = modifier
                .fillMaxWidth()
//                .height(250.dp)
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            )
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                ad.icon?.let { icon ->
                    Image(
                        painter = rememberAsyncImagePainter(icon.drawable),
                        contentDescription = null,
                        modifier = Modifier.size(34.dp).clip(RoundedCornerShape(4.dp))
                    )
                }
                Text(text = ad.headline ?: "", style = MaterialTheme.typography.headlineSmall)
                Text(text = ad.body ?: "", style = MaterialTheme.typography.bodySmall)
                ad.callToAction?.let { cta ->
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {  },
                        border = BorderStroke(1.dp, Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = cta, style = MaterialTheme.typography.labelMedium, color = Color.White)
                    }

                }
            }
        }
    }
}

