package com.tlickteig.minepaper_android

import android.app.Dialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tlickteig.minepaper_android.classses.Constants
import com.tlickteig.minepaper_android.classses.CustomColors
import com.tlickteig.minepaper_android.classses.CustomFonts
import com.tlickteig.minepaper_android.classses.FontAwesomeConstants
import com.tlickteig.minepaper_android.classses.Utilities
import java.net.URL

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
            val context = LocalContext.current
            var isDialogOpen = remember { mutableStateOf(false) }

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
                                    isDialogOpen.value = true
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
                                    Utilities.saveImageToGallery(imageName, context)
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
                                    Utilities.shareImage(imageName, context)
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
                                    Utilities.openImageInBrowser(imageName, context)
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

                if (isDialogOpen.value) {
                    AlertDialog(
                        onDismissRequest = { isDialogOpen.value = false },
                        confirmButton = { /*TODO*/ },
                        modifier = Modifier.padding(bottom = 0.dp),
                        title = {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Set Wallpaper",
                                    fontFamily = CustomFonts.MinecraftFont,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        text = {
                            Column {
                                TextButton(
                                    onClick = {
                                        Utilities.setWallpaper(imageName, context, Utilities.Companion.WallpaperFlags.HOME)
                                        isDialogOpen.value = false
                                    },
                                    modifier = Modifier.fillMaxWidth(),

                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = FontAwesomeConstants.HOUSE_ICON,
                                            fontFamily = CustomFonts.FontAwesome
                                        )

                                        Text(
                                            text = " Set Home Screen Wallpaper",
                                            fontFamily = CustomFonts.MinecraftFont
                                        )
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                TextButton(
                                    onClick = {
                                        Utilities.setWallpaper(imageName, context, Utilities.Companion.WallpaperFlags.LOCK)
                                        isDialogOpen.value = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = FontAwesomeConstants.LOCK_ICON,
                                            fontFamily = CustomFonts.FontAwesome
                                        )

                                        Text(
                                            text = " Set Lock Screen Wallpaper",
                                            fontFamily = CustomFonts.MinecraftFont
                                        )
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                TextButton(
                                    onClick = {
                                        Utilities.setWallpaper(imageName, context, Utilities.Companion.WallpaperFlags.BOTH)
                                        isDialogOpen.value = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = FontAwesomeConstants.SET_WALLPAPER_ICON,
                                            fontFamily = CustomFonts.FontAwesome
                                        )

                                        Text(
                                            text = " Set Both Wallpapers",
                                            fontFamily = CustomFonts.MinecraftFont
                                        )
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                TextButton(
                                    onClick = {
                                        isDialogOpen.value = false
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = FontAwesomeConstants.CANCEL_ICON,
                                            fontFamily = CustomFonts.FontAwesome
                                        )

                                        Text(
                                            text = " Cancel",
                                            fontFamily = CustomFonts.MinecraftFont
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}