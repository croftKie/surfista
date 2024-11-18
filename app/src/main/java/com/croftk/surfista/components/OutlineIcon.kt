package com.croftk.surfista.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.croftk.surfista.R
import com.croftk.surfista.utilities.SearchScreen

@Composable
fun OutlineIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    height: Dp,
    width: Dp,
    onClick: ()->Unit = {}
){
    Box(modifier = modifier
        .clip(CircleShape)
        .background(color = colorResource(R.color.offWhite))
        .padding(10.dp)
    ) {
        ClickableIcon(
            modifier = Modifier.height(height).width(width),
            drawableImage = icon,
            contentDesc = R.string.search_mag_desc,
            click = {
                onClick()
            }
        )
    }
}