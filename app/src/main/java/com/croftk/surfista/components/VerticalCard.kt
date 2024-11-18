package com.croftk.surfista.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.db.entities.Temperature
import com.croftk.surfista.db.entities.Wind
import com.croftk.surfista.utilities.Helpers
import com.croftk.surfista.utilities.calculations.Quality

@Composable
fun VerticalCard(
	board: Board,
	radius: Dp = 12.dp,
	height: Dp = 95.dp,
	clickCard:(String)->Unit = {},
	clickOptionOne: ()->Unit = {},
	clickOptionTwo: ()->Unit = {},
	clickToSave: (String)->Unit = {},
){
	val value = remember { mutableStateOf(board.name) }
	val active = remember { mutableStateOf(false) }

	Box(modifier = Modifier
		.height(130.dp)
		.width(230.dp)
		.clickable { clickCard(value.value) }
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
						modifier = Modifier.fillMaxWidth().fillMaxHeight(0.25f),
						horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
					) {
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text=board.size, fontSize = 15.sp)
						}
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text="${board.id}L", fontSize = 15.sp)
						}
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text=board.type, fontSize = 15.sp)
						}
					}
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.fillMaxHeight(0.6f),
						horizontalAlignment = Alignment.CenterHorizontally,
						verticalArrangement = Arrangement.Center
					) {
						TogglableInput(
							placeholderText = value.value,
							value = value,
							active = active.value,
							onClick = {it ->
								clickToSave(it.value)
								active.value = !active.value
							}
						)
					}
					Row(
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					){
						ClickableIcon(
							modifier = Modifier.height(30.dp),
							drawableImage = R.drawable.edit,
							contentDesc = R.string.search_mag_desc,
							click = {
								active.value = !active.value
								clickOptionOne()
							}
						)
						ClickableIcon(
							modifier = Modifier.height(30.dp),
							drawableImage = R.drawable.trash,
							contentDesc = R.string.search_mag_desc,
							click = {clickOptionTwo()}
						)
					}
				}
			}
		}
	}
}

@Composable
fun VerticalDashboardCard(
	modifier: Modifier = Modifier,
	item: Marine,
	tempItem: Temperature,
	windItem: Wind,
	position: Int,
	selectedDay: MutableIntState,
	radius: Dp = 12.dp,
	selectedHeight: Dp = 200.dp,
	selectedWidth: Dp = 230.dp,
	unselectedHeight: Dp = 160.dp,
	unselectedWidth: Dp = 180.dp,
){
	var avgTemp = Helpers.getAverage(tempItem.temperature)
	var avgWind = Helpers.getAverage(windItem.wind_speed)
	var avgwave = Helpers.getAverage(item.wave_height)
	var avgWaveDirec = Helpers.getAverage(item.wave_direction)
	var avgWindDirec = Helpers.getAverage(windItem.wind_direction)
	val isActive = position == selectedDay.intValue

	val quality = remember { mutableIntStateOf(
		Quality.getScore(
		waveHeight = avgwave,
		preferredWaveSize = 1.5f,
		windDirect = avgWindDirec,
		waveDirect = avgWaveDirec,
		size = 9.0f
	)) }

	Box(modifier = modifier
		.height(if(isActive) selectedHeight else unselectedHeight)
		.width(if(isActive) selectedWidth else unselectedWidth)
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.clip(RoundedCornerShape(radius)),
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
					horizontalAlignment = Alignment.CenterHorizontally
				)  {
					Column(
						modifier = Modifier
							.fillMaxWidth(0.9f),
						horizontalAlignment = Alignment.Start,
						verticalArrangement = Arrangement.Center
					) {
						Text(
							text=item.id,
							fontSize = 20.sp
						)
					}
					Column(
						modifier = Modifier
							.fillMaxWidth(0.9f)
							.fillMaxHeight(0.8f),
						verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
					) {
						Row(
							modifier = Modifier.fillMaxWidth(),
							horizontalArrangement = Arrangement.SpaceAround
						) {
							Capsule("%.0f kp/h".format(avgWind), R.drawable.wind, size = if (isActive){"m"} else {"s"})
							Capsule("%.0f*c".format(avgTemp), R.drawable.sun, size = if (isActive){"m"} else {"s"})
						}
						Row(
							modifier = Modifier.fillMaxWidth(),
							horizontalArrangement = Arrangement.SpaceAround
						) {
							Capsule("${item.wave_height.split(",").maxOrNull()}m", R.drawable.uparrow, size = if (isActive){"m"} else {"s"})
							Capsule("${item.wave_height.split(",").minOrNull()}m", R.drawable.downarrow, size = if (isActive){"m"} else {"s"})
						}
					}
					Row (modifier = Modifier
						.height(15.dp)
						.fillMaxWidth(0.9f)
						.clip(RoundedCornerShape(16.dp))
						.background(
							when(quality.intValue){
								1 -> colorResource(R.color.red)
								2 -> colorResource(R.color.orange)
								3 -> colorResource(R.color.green)
								else -> {
									colorResource(R.color.offWhite)
								}
							})
					) {}
				}
			}
		}
	}
}