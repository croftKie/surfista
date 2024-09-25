package com.croftk.surfista.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.croftk.surfista.R

@Composable
fun SearchResultModifier():Modifier {
	return Modifier.clip(RoundedCornerShape(12.dp))
		.background(colorResource(R.color.white))
		.width(350.dp)
		.padding(12.dp)
}