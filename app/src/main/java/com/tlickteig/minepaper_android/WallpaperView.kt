package com.tlickteig.minepaper_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tlickteig.minepaper_android.classses.Constants
import com.tlickteig.minepaper_android.classses.CustomColors

class WallpaperView : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = this.intent.extras
        var imageName = extras!!.getString("imageName")
        if (imageName == null) {
            imageName = ""
        }

        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = CustomColors.TitleBarColor
                            ),
                            title = {

                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .background(color = CustomColors.BackgroundColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            AsyncImage(
                                model = "${Constants.CDN_URL}/${imageName}",
                                contentDescription = null,
                                modifier = Modifier.clip(RoundedCornerShape(10))
                            )
                        }
                    }
                }
            }
        }
    }
}