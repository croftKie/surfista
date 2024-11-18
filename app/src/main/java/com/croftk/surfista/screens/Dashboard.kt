package com.croftk.surfista.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import co.yml.charts.axis.AxisData
//import co.yml.charts.common.model.Point
//import co.yml.charts.ui.linechart.LineChart
//import co.yml.charts.ui.linechart.model.GridLines
//import co.yml.charts.ui.linechart.model.IntersectionPoint
//import co.yml.charts.ui.linechart.model.Line
//import co.yml.charts.ui.linechart.model.LineChartData
//import co.yml.charts.ui.linechart.model.LinePlotData
//import co.yml.charts.ui.linechart.model.LineStyle
//import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
//import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
//import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.croftk.surfista.BuildConfig
import com.croftk.surfista.R
import com.croftk.surfista.components.ClickableIcon
import com.croftk.surfista.components.Empty
import com.croftk.surfista.ui.theme.SurfistaTheme
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.OutlineIcon
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.components.TabButton
import com.croftk.surfista.components.Tutorial
import com.croftk.surfista.components.VerticalDashboardCard
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.db.entities.Temperature
import com.croftk.surfista.db.entities.Wind
import com.croftk.surfista.utilities.Helpers
import com.croftk.surfista.utilities.SearchScreen
import com.croftk.surfista.utilities.animations.moveElement
import com.croftk.surfista.utilities.calculations.Quality
import com.croftk.surfista.utilities.httpServices.GeoServices
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import kotlinx.coroutines.CoroutineScope
//import com.jaikeerthick.composable_graphs.composables.line.LineGraph
//import com.jaikeerthick.composable_graphs.composables.line.model.LineData
//import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
//import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
//import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphVisibility
//import com.jaikeerthick.composable_graphs.style.LabelPosition
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

@Composable
fun Dashboard(innerPadding: PaddingValues, navController: NavHostController, db: AppDatabase){
	var isActive = remember { mutableStateOf(0) }
	var tableSelected = remember { mutableStateOf(0) }
	val selectedDay = remember { mutableIntStateOf(0) }
	val openDialog = remember { mutableStateOf(false) }
	val dialogData = remember { mutableStateOf<List<String>>(listOf()) }

	val waveData = db.MarineDao().getMarine()
	val tempData = db.TempDao().getTempData()
	val windData = db.WindDao().getWindData()

	val tableAnimActive = remember {mutableStateOf(false)}
	val offset by moveElement(
		transformX = 400.dp,
		resetActive = true,
		active = tableAnimActive,
		finishedListener = {
			tableSelected.value = isActive.value
		}
	)


	Column(modifier = Modifier
		.fillMaxWidth()
		.fillMaxHeight()
		.background(colorResource(R.color.grenTurq))
		.padding(innerPadding)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(0.4f),
		){
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(20.dp),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				Column(
					modifier = Modifier
						.fillMaxWidth(0.85f)
				) {
					Text(
						color = colorResource(R.color.offWhite),
						text = "Day By Day Report",
						fontSize = 30.sp,
					)
					if(waveData.isNotEmpty()){
						Text(
							color = colorResource(R.color.offWhite),
							text = "7 Day surf forecast for Santander, ES",
							fontSize = 15.sp,
						)
					} else {
						Text(
							color = colorResource(R.color.offWhite),
							text = "7 Day surf forecast",
							fontSize = 15.sp,
						)
					}
				}
				OutlineIcon(
					icon = R.drawable.mag,
					height = 30.dp,
					width = 30.dp,
					onClick = {
						navController.navigate(SearchScreen.route)
					}
				)
			}
			if(waveData.isNotEmpty()){
				DayCardRow(
					adjustablePadding = 20.dp,
					waveData,
					tempData,
					windData,
					selectedDay,
					navController,
					tableAnimActive
				)
			} else {
				Tutorial()
			}
		}
		Column(modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
			.background(colorResource(R.color.offWhite)),
		) {
			Row(
				modifier = Modifier
					.padding(horizontal = 20.dp, vertical = 10.dp)
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				TabButton(
					text = "Waves",
					drawable = R.drawable.wave,
					active = isActive.value == 0,
					fontSize = 15.sp,
					onClick = {
						isActive.value = 0
						tableAnimActive.value = !tableAnimActive.value
					}
				)
				TabButton(
					text = "Weather",
					drawable = R.drawable.sun,
					active = isActive.value == 1,
					fontSize = 15.sp,
					onClick = {
						isActive.value = 1
						tableAnimActive.value = !tableAnimActive.value
					}
				)
				TabButton(
					text = "Wind",
					drawable = R.drawable.wind,
					active = isActive.value == 2,
					fontSize = 15.sp,
					onClick = {
						isActive.value = 2
						tableAnimActive.value = !tableAnimActive.value
					}
				)
			}
			if(waveData.isNotEmpty()){
				when(tableSelected.value){
					0 -> Table(
						Modifier.offset { offset },
						timeData = waveData[selectedDay.intValue].time.split(","),
						rowOneValues = waveData[selectedDay.intValue].wave_height.split(","),
						rowTwoValues = waveData[selectedDay.intValue].wave_period.split(","),
						rowThreeValues = waveData[selectedDay.intValue].wave_direction.split(","),
						openDialog = openDialog,
						dialogData = dialogData
						)
					1 -> Table(
						Modifier.offset { offset },
						timeData = tempData[selectedDay.intValue].time.split(","),
						rowOneValues = tempData[selectedDay.intValue].temperature.split(","),
						rowTwoValues = tempData[selectedDay.intValue].rain.split(","),
						rowThreeValues = tempData[selectedDay.intValue].cloud_cover.split(","),
						valueTypes = listOf("°C", "mm", "%"),
						openDialog = openDialog,
						dialogData = dialogData
						)
					2 -> Table(
						Modifier.offset { offset },
						timeData = windData[selectedDay.intValue].time.split(","),
						rowOneValues = windData[selectedDay.intValue].wind_speed.split(","),
						rowTwoValues = windData[selectedDay.intValue].wind_direction.split(","),
						rowThreeValues = windData[selectedDay.intValue].visibility.split(","),
						valueTypes = listOf("km/h", "deg", "km"),
						openDialog = openDialog,
						dialogData = dialogData
						)
					else -> Empty()
				}
			} else {
				Empty()
			}
		}
	}
	GraphDialog(openDialog, dialogData)
}

@Composable
fun GraphDialog(
	openDialog: MutableState<Boolean>,
	dialogData: MutableState<List<String>>
){
	if(openDialog.value){
		Dialog(onDismissRequest = { openDialog.value = false }) {
			Box(
				Modifier
					.fillMaxWidth()
					.height(300.dp)
					.clip(shape = RoundedCornerShape(16.dp))
					.background(Color.White)
			){
				LineGraphComp(dialogData.value)
			}
		}
	}
}


@Composable
fun LineGraphComp(dialogData: List<String>){
	val data: MutableList<Double> = mutableListOf<Double>()
	dialogData.forEach {it ->
		data.add(it.toDouble())
	}
	LineChart(
		modifier = Modifier.fillMaxSize().padding(22.dp),
		data = listOf(
			Line(
				label = "",
				values = data,
				color = SolidColor(Color(0xFF23af92)),
				firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
				secondGradientFillColor = Color.Transparent,
				strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
				gradientAnimationDelay = 1000,
				drawStyle = DrawStyle.Stroke(width = 2.dp),
			),

		),
		animationMode = AnimationMode.Together(delayBuilder = {
			it * 500L
		}),
	)
}

@Composable
fun DayCardRow(
	adjustablePadding: Dp,
	data: List<Marine>,
	tempData: List<Temperature>,
	windData: List<Wind>,
	selectedDay: MutableIntState,
	navController: NavHostController,
	tableAnimActive: MutableState<Boolean>
){

	Column(
		modifier = Modifier
			.padding(12.dp)
			.clip(shape = RoundedCornerShape(12.dp)),
		verticalArrangement = Arrangement.spacedBy(18.dp)
	) {
		val state = rememberLazyListState()
		LazyRow(modifier = Modifier
			.height(250.dp)
			.padding(bottom = adjustablePadding, start = adjustablePadding / 2),
			state = state,
			flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(12.dp))
		{
			items(data.size) { it ->
				VerticalDashboardCard(
					Modifier
						.clickable {
						tableAnimActive.value = !tableAnimActive.value
						selectedDay.intValue = it
					},
					data[it],
					tempData[it],
					windData[it],
					it,
					selectedDay
				)
			}
		}
	}
}

@Composable
fun Table(
	modifier: Modifier = Modifier,
	rows: Int = 4,
	rowHeight: Dp = 80.dp,
	columnHeight: Dp = 80.dp,
	timeData: List<String>,
	rowOneValues: List<String>,
	rowTwoValues: List<String>,
	rowThreeValues: List<String>,
	icons: List<Int> = listOf(R.drawable.clock, R.drawable.wave, R.drawable.stopwatch, R.drawable.compass),
	valueTypes: List<String> = listOf("m", "s", "deg"),
	openDialog: MutableState<Boolean>,
	dialogData: MutableState<List<String>>
){
	val scrollState = rememberScrollState()
	val vScrollState = rememberScrollState()
	Row(modifier = modifier
		.padding(top = 18.dp, start = 18.dp, end = 18.dp, bottom = 18.dp)
		.horizontalScroll(scrollState)
		.verticalScroll(vScrollState)
	) {
		timeData.forEachIndexed {index, _ ->
			Column {
				for (j in 1 .. rows){
					Column(
						modifier = Modifier
							.padding(6.dp)
							.clip(shape = RoundedCornerShape(12.dp))
							.background(colorResource(R.color.lightGrey))
							.border(BorderStroke(1.dp, colorResource(R.color.borderDark)))
							.height(rowHeight)
							.width(columnHeight)
							.clickable {
								if(index == 0 && j > 1){
									openDialog.value = true;
									when(j){
										2 -> dialogData.value = rowOneValues
										3 -> dialogData.value = rowTwoValues
										4 -> dialogData.value = rowThreeValues
									}
								}
							},
						horizontalAlignment = Alignment.CenterHorizontally,
						verticalArrangement = Arrangement.Center
					){
						if (index == 0){
							when(j){
								1 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(icons[0]), contentDescription = "")
								2 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(icons[1]), contentDescription = "")
								3 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(icons[2]), contentDescription = "")
								4 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(icons[3]), contentDescription = "")

							}
						} else if (j == 1){
							Text(color = colorResource(R.color.darkGray), text = timeData[index].substring(12))
						} else {
							val v1 = if (rowOneValues[index] !== "null") { "${rowOneValues[index]}${valueTypes[0]}" } else { "N/A" }
							val v2 = if (rowTwoValues[index] !== "null") { "${rowTwoValues[index]}${valueTypes[1]}" } else { "N/A" }
							val v3 = if (rowThreeValues[index] !== "null") { "${rowThreeValues[index].toFloat() / 1000}${valueTypes[2]}" } else { "N/A" }

							when(j){
								2 -> Text(color = colorResource(R.color.darkGray), text = v1)
								3 -> Text(color = colorResource(R.color.darkGray), text = v2)
								4 -> Text(color = colorResource(R.color.darkGray), text = v3)
							}
						}
					}
				}
			}
		}
	}
}