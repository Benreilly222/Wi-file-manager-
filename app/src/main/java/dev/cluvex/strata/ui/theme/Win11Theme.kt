package dev.cluvex.strata.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Windows 11 File Explorer palette: Mica backgrounds, white cards, #0067C0 accent. */
object Win11 {
    val FolderYellow = Color(0xFFFFC83D)
    val DriveBarRed = Color(0xFFC42B1C)

    private val light = lightColorScheme(
        primary = Color(0xFF0067C0),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFCCE4F7),
        onPrimaryContainer = Color(0xFF1B1B1B),
        secondary = Color(0xFF5C5C5C),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFE5E5E5),
        onSecondaryContainer = Color(0xFF1B1B1B),
        tertiary = Color(0xFF0067C0),
        onTertiary = Color.White,
        tertiaryContainer = Color(0xFFCCE4F7),
        onTertiaryContainer = Color(0xFF1B1B1B),
        background = Color(0xFFF3F3F3),
        onBackground = Color(0xFF1B1B1B),
        surface = Color(0xFFF3F3F3),
        onSurface = Color(0xFF1B1B1B),
        surfaceVariant = Color(0xFFE5E5E5),
        onSurfaceVariant = Color(0xFF5C5C5C),
        surfaceTint = Color(0xFF0067C0),
        outline = Color(0xFF8A8A8A),
        outlineVariant = Color(0xFFE0E0E0),
        error = Color(0xFFC42B1C),
        onError = Color.White,
        surfaceDim = Color(0xFFE6E6E6),
        surfaceBright = Color(0xFFFFFFFF),
        surfaceContainerLowest = Color(0xFFFFFFFF),
        surfaceContainerLow = Color(0xFFF3F3F3),
        surfaceContainer = Color(0xFFF9F9F9),
        surfaceContainerHigh = Color(0xFFFFFFFF),
        surfaceContainerHighest = Color(0xFFEBEBEB),
    )

    private val dark = darkColorScheme(
        primary = Color(0xFF60CDFF),
        onPrimary = Color(0xFF003355),
        primaryContainer = Color(0xFF0B4A72),
        onPrimaryContainer = Color.White,
        secondary = Color(0xFFCFCFCF),
        onSecondary = Color(0xFF202020),
        secondaryContainer = Color(0xFF3A3A3A),
        onSecondaryContainer = Color.White,
        tertiary = Color(0xFF60CDFF),
        onTertiary = Color(0xFF003355),
        tertiaryContainer = Color(0xFF0B4A72),
        onTertiaryContainer = Color.White,
        background = Color(0xFF202020),
        onBackground = Color(0xFFF3F3F3),
        surface = Color(0xFF202020),
        onSurface = Color(0xFFF3F3F3),
        surfaceVariant = Color(0xFF3A3A3A),
        onSurfaceVariant = Color(0xFFCFCFCF),
        surfaceTint = Color(0xFF60CDFF),
        outline = Color(0xFF8A8A8A),
        outlineVariant = Color(0xFF3D3D3D),
        error = Color(0xFFFF99A4),
        onError = Color(0xFF3B0A0F),
        surfaceDim = Color(0xFF1C1C1C),
        surfaceBright = Color(0xFF383838),
        surfaceContainerLowest = Color(0xFF1C1C1C),
        surfaceContainerLow = Color(0xFF272727),
        surfaceContainer = Color(0xFF2B2B2B),
        surfaceContainerHigh = Color(0xFF323232),
        surfaceContainerHighest = Color(0xFF3A3A3A),
    )

    fun scheme(dark: Boolean): ColorScheme = if (dark) this.dark else light

    /** Windows 11 uses 4dp controls and 8dp cards/flyouts. */
    val shapes = Shapes(
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(8.dp),
        extraLarge = RoundedCornerShape(12.dp),
        largeIncreased = RoundedCornerShape(8.dp),
        extraLargeIncreased = RoundedCornerShape(12.dp),
        extraExtraLarge = RoundedCornerShape(16.dp),
    )
}
