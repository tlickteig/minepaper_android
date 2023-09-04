package com.example.minepaper_android

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.minepaper_android.classses.Utilities
import com.example.minepaper_android.ui.theme.MinePaperTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinePaperTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var isLoading by remember {
                        mutableStateOf(true)
                    }

                    if (isLoading) {
                        CircularProgressIndicator()
                        Text("Downloading images...")
                    }

                    if (!isLoading) {
                        var images = Utilities.returnImageListFromServer()
                        images.forEach { image ->
                            Text(image)
                        }
                    }

                    TextButton(
                        onClick = {
                            isLoading = !isLoading
                        }
                    ) {
                        Text("Toggle loading")
                    }
                }
            }
        }
    }
}