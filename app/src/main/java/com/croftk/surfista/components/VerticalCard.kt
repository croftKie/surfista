package com.croftk.surfista.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
fun VerticalCard(
	rightContentBg: Color = colorResource(R.color.offWhite),
	radius: Dp = 12.dp,
	height: Dp = 95.dp,
){
	Box(modifier = Modifier
		.height(230.dp)
		.width(230.dp)
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.clip(RoundedCornerShape(radius)),
			border = BorderStroke(1.dp, colorResource(R.color.borderDark))
		){
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.fillMaxWidth()
					.background(colorResource(R.color.lightGrey))
			) {
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.fillMaxWidth()
						.padding(8.dp),
					verticalArrangement = Arrangement.SpaceEvenly,
					horizontalAlignment = Alignment.End
				)  {
					Row(
						modifier = Modifier.fillMaxWidth().fillMaxHeight(0.15f),
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text="10ft", fontSize = 15.sp)
						}
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text="70 litres", fontSize = 15.sp)
						}
					}
					Box(
						modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)
					) {

					}
					ClickableIcon(
						modifier = Modifier.height(30.dp),
						drawableImage = R.drawable.add,
						contentDesc = R.string.search_mag_desc,
						click = {}
					)
				}
			}
		}
	}
}




@Preview
@Composable
fun PrevHoriCard(){
	Surface(Modifier.background(colorResource(R.color.offWhite)).padding(12.dp)) {
		VerticalCard()
	}
}





