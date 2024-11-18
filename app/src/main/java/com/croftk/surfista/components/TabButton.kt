package com.croftk.surfista.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.croftk.surfista.R

@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 20.sp,
    drawable: Int = R.drawable.ic_launcher_foreground,
    active: Boolean = false,
    iconActive: Boolean = true,
    onClick: () -> Unit
){

    val bgColor = if (active) R.color.lightTurq else R.color.offWhite
    val width = if(active) 120.dp else 40.dp
    Column(
        modifier = modifier
            .widthIn(40.dp, 120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(bgColor))
            .clickable(enabled = true, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .width(width)
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ImageIcon(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                drawableImage = drawable,
                contentDesc = R.string.search_mag_desc
            )
            if(active){
                Text(text = text, fontSize = fontSize)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabButton(){
    TabButton(text = "Click") {
        println("Hello World")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabButtonActive(){
    TabButton(text = "Click", active = true) {
        println("Hello World")
    }
}