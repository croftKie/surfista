package com.croftk.surfista.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.croftk.surfista.R

@Composable
fun SearchResultModifier():Modifier {
	return Modifier.clip(RoundedCornerShape(12.dp))
		.background(colorResource(R.color.grenTurq))
		.width(350.dp)
		.padding(12.dp)
}
@Composable
fun QuiverFilterModifier():Modifier {
	return Modifier
		.background(colorResource(R.color.offWhite))
		.fillMaxHeight()
}