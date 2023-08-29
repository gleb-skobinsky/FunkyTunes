package example.funkytunes.view

import androidx.compose.ui.Modifier
import example.funkytunes.model.ScalableState

actual fun Modifier.addUserInput(state: ScalableState) =
    addTouchUserInput(state)
