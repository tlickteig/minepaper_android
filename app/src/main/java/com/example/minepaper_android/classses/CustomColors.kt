package com.example.minepaper_android.classses

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

class CustomColors {
    companion object {
        var BackgroundColor: Color = Color.Black
            get() {
                var output = Color(0xFF2D2D2D)
                if (Utilities.isAppInDarkMode()) {
                    return Color(0xFF2D2D2D)
                }

                return output
            }

        var TextColor: Color = Color.Black
            get() {
                var output = Color(0xFFEAEAEA)
                if (Utilities.isAppInDarkMode()) {
                    return Color(0xFFEAEAEA)
                }

                return output
            }

        var TitleBarColor = Color.Black
            get() {
                var output = Color(0xFF535353)
                if (Utilities.isAppInDarkMode()) {
                    return Color(0xFF535353)
                }

                return output
            }
    }
}