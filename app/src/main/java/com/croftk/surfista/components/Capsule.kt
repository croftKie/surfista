package com.croftk.surfista.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.croftk.surfista.R

@Composable
fun Capsule(
    text: String = "value",
    icon: Int = R.drawable.wind,
    iconActive: Boolean = true,
    size: String = "s"
){
    when (size) {
        "s" -> {
            Row(
                modifier = Modifier
                    .width(70.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(colorResource(R.color.lightTurq))
                    .padding(vertical = 2.dp, horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                if(iconActive){
                    Image(
                        modifier = Modifier.height(10.dp),
                        painter = painterResource(icon),
                        contentDescription = "")
                }
                Text(text=text, fontSize = 12.sp)
            }
        }
        "m" -> {
            Row(
                modifier = Modifier
                    .width(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(colorResource(R.color.lightTurq))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                if(iconActive){
                    Image(
                        modifier = Modifier.height(13.dp),
                        painter = painterResource(icon),
                        contentDescription = "")
                }
                Text(text=text, fontSize = 13.sp)
            }
        }
        "l" -> {
            Row(
                modifier = Modifier
                    .width(90.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(colorResource(R.color.lightTurq))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                if(iconActive){
                    Image(
                        modifier = Modifier.height(15.dp),
                        painter = painterResource(icon),
                        contentDescription = "")
                }
                Text(text=text, fontSize = 15.sp)
            }
        }
    }
}