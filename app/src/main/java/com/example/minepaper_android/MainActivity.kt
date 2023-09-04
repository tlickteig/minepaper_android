package com.example.minepaper_android

import android.media.Image
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.minepaper_android.classses.Constants
import com.example.minepaper_android.classses.Utilities
import com.example.minepaper_android.ui.theme.MinePaperTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    companion object {
        var fullImageList: List<String> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinePaperTheme {
                val page = remember { mutableStateOf(1) }
                val loading = remember { mutableStateOf(false) }
                val itemList = remember { mutableStateListOf<String>() }
                val listState = rememberLazyListState()

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(itemList) { item ->
                        //Text(text = item, modifier = Modifier.padding(10.dp))
                        AsyncImage(
                            model = "${Constants.CDN_URL}/${item}",
                            contentDescription = null
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
                    loading.value = true
                    itemList.addAll(getListOfImagesOnPage(page.value))
                    loading.value = false
                }

                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    }
                    .collectLatest { index ->
                        if (!loading.value && index != null && index >= itemList.size - 5) {
                            page.value++
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