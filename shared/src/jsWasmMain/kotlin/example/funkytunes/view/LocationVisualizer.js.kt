package example.funkytunes.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import example.funkytunes.painterResourceCached
import org.jetbrains.compose.resources.*

@OptIn(ExperimentalResourceApi::class)
@Composable
internal actual fun LocationVisualizer(modifier: Modifier) {
    Image(
        painter = painterResourceCached("dummy_map.png"),
        contentDescription = "Map",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
