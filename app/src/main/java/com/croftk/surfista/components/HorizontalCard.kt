package com.croftk.surfista.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.croftk.surfista.R
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.Board

@Composable
fun HorizontalCard(
	rightContentBg: Color = colorResource(R.color.offWhite),
	radius: Dp = 12.dp,
	height: Dp = 100.dp,
){
	Row(modifier = Modifier
		.height(height)
		.fillMaxWidth()
		.background(colorResource(R.color.offWhite)),
		horizontalArrangement = Arrangement.spacedBy(6.dp)
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth(0.7f)
				.fillMaxHeight(),
			shape = RoundedCornerShape(topStart = radius, bottomStart = radius ),
			elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
		){
			Row(
				modifier = Modifier
					.fillMaxHeight()
					.fillMaxWidth()
					.background(colorResource(R.color.offWhite))
			) {
				Image(
					modifier = Modifier.fillMaxWidth(0.2f).fillMaxHeight(),
					contentScale = ContentScale.Crop,
					painter =  painterResource(R.drawable.beach),
					contentDescription = ""
				)
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.padding(6.dp),
					verticalArrangement = Arrangement.SpaceAround
				) {
					Text(color = colorResource(R.color.grenTurq), text="Longboard", fontSize = 23.sp)
					Text(text="Length: 9ft / 2.3m", fontSize = 15.sp)
					Text(text="Est. Volume: 70 litres", fontSize = 15.sp)
				}
			}
		}
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(),
			shape = RoundedCornerShape(topEnd = radius, bottomEnd = radius),
			elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
		) {
			Column(modifier = Modifier
				.fillMaxHeight()
				.fillMaxWidth()
				.background(rightContentBg)
				.padding(12.dp),
				verticalArrangement = Arrangement.Center
			) {
				Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
					SolidButton(text = "Add", iconActive = false) {}
					SolidButton(text = "Delete", iconActive = false) {}
				}
			}
		}
	}
}


@Preview
@Composable
fun PrevHoriCard(){
	Surface(Modifier.background(colorResource(R.color.offWhite)).padding(12.dp)) {
		HorizontalCard()
	}
}





