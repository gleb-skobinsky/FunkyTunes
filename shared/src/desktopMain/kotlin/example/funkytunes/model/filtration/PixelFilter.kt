package example.funkytunes.model.filtration

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.toComposeImageBitmap
import example.funkytunes.core.BitmapFilter
import example.funkytunes.utils.applyPixelFilter

class PixelFilter : BitmapFilter {

    override fun apply(bitmap: ImageBitmap): ImageBitmap {
        return applyPixelFilter(bitmap.toAwtImage()).toComposeImageBitmap()
    }
}
