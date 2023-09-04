package com.example.minepaper_android

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.minepaper_android.ui.theme.MinePaperTheme

class MainActivity : ComponentActivity() {

    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinePaperTheme {
                // A surface container using the 'background' color from the theme
                AnimatedVisibility(setVisible(isLoading)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CircularProgressIndicator(
                            color = Color.Red
                        )
                    }
                }
                else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Text(
                            text = "I'm not loading!"
                        )
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