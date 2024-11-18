package com.croftk.surfista.utilities.animations

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun moveElement(
    transformX: Dp = 0.dp,
    transformY: Dp = 0.dp,
    type: String = "horizontal",
    resetActive: Boolean = false,
    flipped: Boolean = false,
    active: MutableState<Boolean> = mutableStateOf(false),
    finishedListener: ()->Unit = {}
): State<IntOffset>{
    val XpxToMove = with(LocalDensity.current){transformX.toPx().roundToInt()}
    val YpxToMove = with(LocalDensity.current){transformY.toPx().roundToInt()}

    val offset = animateIntOffsetAsState(
        targetValue = if (active.value){
            IntOffset(XpxToMove, YpxToMove)
        } else {
            IntOffset.Zero
        },
        finishedListener = { IntOffset ->
            if(resetActive){
                if(type == "horizontal" && IntOffset.x == XpxToMove){
                    finishedListener()
                } else if (type == "vertical" && IntOffset.y == YpxToMove) {
                    finishedListener()
                } else if (type == "diagonal" && (IntOffset.x == XpxToMove || IntOffset.y == YpxToMove)){
                    finishedListener()
                }
                if(active.value){
                    active.value = !active.value
                }
            } else if (!resetActive) {
                finishedListener()
            }
        },
        label = "offset"
    )

    return offset

}