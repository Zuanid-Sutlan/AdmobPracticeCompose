package com.devStudio.admobpracticecompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devStudio.admobpracticecompose.ui.theme.AdmobPracticeComposeTheme

class MainActivity2 : ComponentActivity() {

    private val dataList = mutableListOf<Data>()

    override fun onStart() {
        super.onStart()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val listSize = remember { mutableStateOf(0) }

            LaunchedEffect(listSize.value) {
                dataList.clear()

                for (i in 1..listSize.value) {
                    dataList.add(Data("Title $i", "Description $i"))
                }
            }

            AdmobPracticeComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    IconButton(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Blue),
                        onClick = {
                            listSize.value++
                            Toast.makeText(this, "added", Toast.LENGTH_SHORT).show()
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "add in list",
                            tint = Color.White
                        )
                    }
                }) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        Greeting2(
                            name = "Android",
                            modifier = Modifier
                                .padding(innerPadding)
                                .align(Alignment.TopCenter)
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {

                            val dataSize = dataList.size
                            /*items(dataSize + (dataSize / 2) + if (dataSize % 2 == 1) 1 else 0) { index ->
                                val dataIndex = index - (index / 2)
                                when {
                                    shouldShowAd(
                                        index,
                                        dataSize
                                    ) && NativeAdManager.isNativeAdLoaded() -> {
                                        NativeAdView(ad = NativeAdManager.nativeAd)
                                    }

                                    dataIndex < dataSize -> {
                                        ItemView(dataList[dataIndex])
                                    }
                                }
                            }*/
                            items(dataList.size) {
                                ItemView(dataList[it])
                                if (it % 2 == 0 && NativeAdManager.isNativeAdLoaded()) {
                                    NativeAdView(ad = NativeAdManager.nativeAd)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

private fun shouldShowAd(index: Int, dataSize: Int): Boolean {
    return (index > 0 && index % 3 == 0) || (dataSize == 1 && index == 1)
}

data class Data(val title: String, val desc: String)

@Composable
fun ItemView(data: Data) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(250.dp)
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp), contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Text(text = data.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = data.desc)
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AdmobPracticeComposeTheme {
        Greeting2("Android")
    }
}