package example.funkytunes.core

import androidx.compose.ui.graphics.ImageBitmap

interface BitmapFilter {
    fun apply(bitmap: ImageBitmap): ImageBitmap
}
