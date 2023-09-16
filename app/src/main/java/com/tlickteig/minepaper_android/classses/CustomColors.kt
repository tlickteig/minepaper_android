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
                Color(0xFF4A4A4A)
            } else {
                Color(0xFFEDEDED)
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

        val DialogBackgroundColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFF535353)
            } else {
                Color.White
            }

        val TextButtonTextColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFFA3B4FF)
            } else {
                Color(0xFF626FFC)
            }

        val FilledButtonTextColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFFEAEAEA)
            } else {
                Color(0xFF2F41FA)
            }

        val FilledButtonBackgroundColor: Color
            @Composable
            get() = if (isSystemInDarkTheme()) {
                Color(0xFF4F8B8C)
            } else {
                Color(0xFFB3E7E8)
            }
    }
}