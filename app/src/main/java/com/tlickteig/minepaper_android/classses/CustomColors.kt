package com.tlickteig.minepaper_android.classses

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

class CustomColors {

    companion object {
        val BackgroundColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFF2D2D2D)
            } else {
                Color(0xFFE6E6E6)
            }

        val TextColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFFEAEAEA)
            } else {
                Color(0xFF4A4A4A)
            }

        val FadedTextColor: Color
            @Composable
            get() = Color.LightGray

        val TitleBarColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFF535353)
            } else {
                Color(0xFFF5F5F5)
            }

        val ErrorColor: Color
            @Composable
            get() = Color.Red

        val TextButtonTextColor: Color
            @Composable
            get() = Color.LightGray

        val FilledButtonTextColor: Color
            @Composable
            get() = Color.LightGray

        val FilledButtonBackgroundColor: Color
            @Composable
            get() = Color.Blue
    }
}