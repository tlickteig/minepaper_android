package com.tlickteig.minepaper_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.dtdi.analytics.Results
import com.tlickteig.minepaper_android.classses.Constants
import com.tlickteig.minepaper_android.classses.CustomColors
import com.tlickteig.minepaper_android.classses.CustomFonts
import com.tlickteig.minepaper_android.classses.FontAwesomeConstants
import com.tlickteig.minepaper_android.classses.Utilities
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    companion object {
        var fullImageList: List<String> = mutableListOf()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = CustomColors.TitleBarColor
                            ),
                            title = {
                                Row (
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.size(40.dp, 40.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = "Main app logo"
                                        )
                                    }

                                    Spacer(
                                        modifier = Modifier.width(20.dp)
                                    )

                                    Text(
                                        text = "MinePaper",
                                        color = CustomColors.TextColor,
                                        fontFamily = CustomFonts.MinecraftFont
                                    )

                                    Text(
                                        text = " v",
                                        color = CustomColors.FadedTextColor,
                                        fontFamily = CustomFonts.MinecraftFont
                                    )

                                    Text(
                                        text = Utilities.getVersionString(),
                                        color = CustomColors.FadedTextColor,
                                        fontFamily = CustomFonts.MinecraftFont
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    val page = remember { mutableStateOf(0) }
                    val loading = remember { mutableStateOf(false) }
                    val itemList = remember { mutableStateListOf<String>() }
                    val listState = rememberLazyListState()
                    var hasErrorHappened = remember { mutableStateOf(false) }

                    if (!hasErrorHappened.value) {
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(color = CustomColors.BackgroundColor)
                        ) {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(itemList) { item ->
                                    Spacer(
                                        modifier = Modifier.width(20.dp)
                                    )

                                    AsyncImage(
                                        model = "${Constants.CDN_URL}/${item}",
                                        contentDescription = null,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10))
                                            .clickable(onClick = {
                                                var intent =
                                                    Intent(context, WallpaperView::class.java)
                                                intent.putExtra("imageName", item)
                                                context.startActivity(intent)
                                            })
                                    )
                                }

                                item {
                                    if (loading.value) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(50.dp),
                                                strokeWidth = 2.dp
                                            )
                                        }
                                    }
                                }
                            }

                            LaunchedEffect(key1 = page.value) {
                                try {
                                    loading.value = true
                                    itemList.addAll(getListOfImagesOnPage(page.value))
                                    loading.value = false
                                } catch (e: Exception) {
                                    hasErrorHappened.value = true
                                }

                            }

                            LaunchedEffect(listState) {
                                snapshotFlow {
                                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                }
                                    .collectLatest { index ->
                                        if (!loading.value && index != null && index >= itemList.size - 1) {
                                            page.value++
                                        }
                                    }
                            }
                        }
                    }
                    else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(color = CustomColors.BackgroundColor)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = FontAwesomeConstants.ERROR_ICON,
                                fontFamily = CustomFonts.FontAwesome,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = CustomColors.ErrorColor,
                                fontSize = 30.sp
                            )

                            Text(
                                text = "An error has occurred. Sorry",
                                fontFamily = CustomFonts.MinecraftFont,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = CustomColors.TextColor
                            )

                            TextButton(
                                onClick = {
                                    hasErrorHappened.value = false
                                }
                            ) {
                                Text(
                                    text = "Retry",
                                    fontFamily = CustomFonts.MinecraftFont,
                                    textAlign = TextAlign.Center,
                                    color = CustomColors.TextButtonColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getListOfImagesOnPage(page: Int): List<String> {

        if (!fullImageList.any()) {
            fullImageList = Utilities.returnImageListFromServer()
        }
        var output: List<String> = mutableListOf()

        val windowedList = fullImageList.windowed(10, 10, true)
        if (windowedList.count() > page) {
            output = windowedList[page]
        }

        return output
    }
}