package com.tlickteig.minepaper_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.tlickteig.minepaper_android.classses.CustomFonts
import com.tlickteig.minepaper_android.classses.FontAwesomeConstants

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
                                Text(
                                    text = "Back",
                                    color = CustomColors.TextColor,
                                    fontFamily = CustomFonts.MinecraftFont
                                )
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        finish()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxHeight()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(color = CustomColors.BackgroundColor)
                            .fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            AsyncImage(
                                model = "${Constants.CDN_URL}/${imageName}",
                                contentDescription = null,
                                modifier = Modifier.clip(RoundedCornerShape(10))
                            )

                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )

                            FilledTonalButton(
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = FontAwesomeConstants.SET_WALLPAPER_ICON,
                                    fontFamily = CustomFonts.FontAwesome
                                )

                                Text(
                                    text = " Set Wallpaper",
                                    fontFamily = CustomFonts.MinecraftFont
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )

                            FilledTonalButton(
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = FontAwesomeConstants.DOWNLOAD_ICON,
                                    fontFamily = CustomFonts.FontAwesome
                                )

                                Text(
                                    text = " Save Wallpaper",
                                    fontFamily = CustomFonts.MinecraftFont
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )

                            FilledTonalButton(
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = FontAwesomeConstants.SHARE_ICON,
                                    fontFamily = CustomFonts.FontAwesome
                                )

                                Text(
                                    text = " Share Wallpaper",
                                    fontFamily = CustomFonts.MinecraftFont
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )

                            FilledTonalButton(
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = FontAwesomeConstants.BROWSER_ICON,
                                    fontFamily = CustomFonts.FontAwesome
                                )

                                Text(
                                    text = " View in Browser",
                                    fontFamily = CustomFonts.MinecraftFont
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}