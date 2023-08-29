package example.funkytunes.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import example.funkytunes.model.ScalableState
import example.funkytunes.model.addDragAmount
import example.funkytunes.model.addScale
import example.funkytunes.model.setScale

expect fun Modifier.addUserInput(state: ScalableState): Modifier

fun Modifier.addTouchUserInput(state: ScalableState): Modifier =
    pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            state.addDragAmount(pan)
            state.addScale(zoom - 1f)
        }
    }.pointerInput(Unit) {
        detectTapGestures(
            onDoubleTap = { state.setScale(1f) }
        )
    }
