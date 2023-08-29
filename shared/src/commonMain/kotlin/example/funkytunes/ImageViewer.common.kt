package example.funkytunes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import example.funkytunes.model.CameraPage
import example.funkytunes.model.FullScreenPage
import example.funkytunes.model.GalleryPage
import example.funkytunes.model.MemoryPage
import example.funkytunes.model.Page
import example.funkytunes.model.PhotoGallery
import example.funkytunes.view.CameraScreen
import example.funkytunes.view.FullscreenImage
import example.funkytunes.view.GalleryScreen
import example.funkytunes.view.MemoryScreen
import example.funkytunes.view.NavigationStack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

enum class ExternalImageViewerEvent {
    Foward,
    Back
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ImageViewerCommon(
    dependencies: Dependencies,
    externalEvents: Flow<ExternalImageViewerEvent> = emptyFlow()
) {
    val photoGallery = remember { PhotoGallery(dependencies) }
    val rootGalleryPage = GalleryPage(photoGallery, externalEvents)
    val navigationStack = remember { NavigationStack<Page>(rootGalleryPage) }

    Surface(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(targetState = navigationStack.lastWithIndex(), transitionSpec = {
            val previousIdx = initialState.index
            val currentIdx = targetState.index
            val multiplier = if (previousIdx < currentIdx) 1 else -1
            if (initialState.value is GalleryPage && targetState.value is MemoryPage) {
                fadeIn() with fadeOut(tween(durationMillis = 500, 500))
            } else if (initialState.value is MemoryPage && targetState.value is GalleryPage) {
                fadeIn() with fadeOut(tween(delayMillis = 150))
            } else {
                slideInHorizontally { w -> multiplier * w } with
                        slideOutHorizontally { w -> multiplier * -1 * w }
            }
        }) { (index, page) ->
            when (page) {
                is GalleryPage -> {
                    GalleryScreen(
                        page,
                        photoGallery,
                        dependencies,
                        onClickPreviewPicture = { previewPicture ->
                            navigationStack.push(MemoryPage(previewPicture))
                        },
                        onMakeNewMemory = {
                            navigationStack.push(CameraPage())
                        })
                }

                is FullScreenPage -> {
                    FullscreenImage(
                        picture = page.pictureData,
                        getFilter = { dependencies.getFilter(it) },
                        localization = dependencies.localization,
                        back = {
                            navigationStack.back()
                        }
                    )
                }

                is MemoryPage -> {
                    MemoryScreen(
                        memoryPage = page,
                        dependencies = dependencies,
                        photoGallery = photoGallery,
                        localization = dependencies.localization,
                        onSelectRelatedMemory = { picture ->
                             navigationStack.push(MemoryPage(picture))
                        },
                        onBack = {
                            navigationStack.back()
                        },
                        onHeaderClick = { picture ->
                            navigationStack.push(FullScreenPage(picture))
                        })
                }

                is CameraPage -> {
                    CameraScreen(onBack = {
                        navigationStack.back()
                    })
                }
            }
        }
    }
}
