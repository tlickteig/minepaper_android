package com.tlickteig.minepaper_android.classses

import android.os.Build
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.tlickteig.minepaper_android.R

class CustomFonts {
        companion object {
                val MinecraftFont = FontFamily(
                        Font(R.font.minecraft_regular, FontWeight.Normal),
                        Font(R.font.minecraft_regular, FontWeight.Bold),
                        Font(R.font.minecraft_bold_italic, FontWeight.Bold, FontStyle.Italic),
                        Font(R.font.minecraft_italic, FontWeight.Normal, FontStyle.Italic)
                )

                val FontAwesome = FontFamily(
                        Font(R.font.fa_regular_400, FontWeight.Normal),
                        Font(R.font.fa_solid_900, FontWeight.Bold),
                        Font(R.font.fa_light_300, FontWeight.Light),
                        Font(R.font.fa_thin_100, FontWeight.ExtraLight)
                )
        }
}