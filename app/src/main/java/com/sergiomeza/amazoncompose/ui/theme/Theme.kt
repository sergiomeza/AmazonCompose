package com.sergiomeza.amazoncompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sergiomeza.amazoncompose.R

val Amazon = FontFamily(
    Font(R.font.amazonember_rg, FontWeight.Normal),
    Font(R.font.amazonember_it, FontWeight.Light),
    Font(R.font.amazonember_bd, FontWeight.Bold),
    Font(R.font.amazonmber_he, FontWeight.ExtraBold),
    Font(R.font.amazonember_th, FontWeight.Thin)
)

val AmazonTypho = Typography(
    h1 = TextStyle(
        fontFamily = Amazon,
        fontWeight = FontWeight.Bold,
        fontSize = 96.sp
    ),
    h2 = TextStyle(
        fontFamily = Amazon,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp
    ),
    h3 = TextStyle(
        fontFamily = Amazon,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = Amazon,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Amazon,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Amazon,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
    /*...*/
)

private val DarkColorPalette = darkColors(
    primary = primary,
    primaryVariant = primary,
    secondary = textColorDark
)

private val LightColorPalette = lightColors(
    primary = primary,
    primaryVariant = primary,
    secondary = textColor

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AmazonComposeTheme(
    typography: Typography = AmazonTypho,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}