package com.croftk.surfista.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.BuildConfig
import com.croftk.surfista.R
import com.croftk.surfista.ui.theme.SurfistaTheme
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.components.TabButton
import com.croftk.surfista.utilities.SearchScreen
import com.croftk.surfista.utilities.httpServices.GeoServices
import kotlinx.coroutines.launch


@Composable
fun DayCard(){
	Card(modifier = Modifier
		.width(200.dp)
		.padding(end = 6.dp)
	) {
		Column(
			modifier = Modifier
				.background(Color.White)
				.padding(12.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text("Monday", fontSize = 20.sp);
				Box(modifier = Modifier
					.clip(shape = CircleShape)
					.height(15.dp)
					.width(15.dp)
					.background(colorResource(R.color.green))
				)
			}
			Row (modifier = Modifier.fillMaxWidth()){
				Row(modifier = Modifier
					.fillMaxWidth(0.45f),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(6.dp)
				){
					ImageIcon(
						Modifier.height(15.dp).width(15.dp),
						R.drawable.sun,
						R.string.search_mag_desc
					)
					Text( text = "26°C")
				}
				Row(modifier = Modifier
					.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceEvenly
				){
					ImageIcon(
						Modifier.height(15.dp).width(15.dp),
						R.drawable.wind,
						R.string.search_mag_desc
					)
					Text( text = "9kph")
				}
			}
			Row(modifier = Modifier
				.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text( text = "Daily High: 1.6m")
			}
			Row(modifier = Modifier
				.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text( text = "Daily Low: 0.8m")
			}
		}
	}
}


@Composable
fun DayCardRow(adjustablePadding: Dp){
	val scrollState = rememberScrollState()


	Column(
		modifier = Modifier
			.padding(12.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.background(colorResource(R.color.blue)),
		verticalArrangement = Arrangement.spacedBy(18.dp)
	) {
		Column(
			modifier = Modifier
				.padding(start = 12.dp, top = 12.dp)
		) {
			Text(
				"Day By Day Report",
				fontSize = 30.sp,
			)
			Text("7 Day surf forecast for Santander, ES")
		}
		Row(modifier = Modifier
			.padding(bottom = adjustablePadding, start = adjustablePadding / 2)
			.horizontalScroll(scrollState),
			horizontalArrangement = Arrangement.spacedBy(12.dp)) {
			for (i in 1..7){
				DayCard()
			}
		}
	}
}


@Composable
fun Table(
	modifier: Modifier = Modifier,
	rows: Int = 4,
	columns: Int = 15,
	rowHeight: Dp = 80.dp,
	columnHeight: Dp = 80.dp
){
	val scrollState = rememberScrollState()
	Row(modifier = modifier
		.padding(top = 18.dp, start = 18.dp, end = 18.dp)
		.horizontalScroll(scrollState)

	) {
		for (i in 1..columns){
			Column {
				for (j in 1 .. rows){
					Column(
						modifier = Modifier
							.padding(6.dp)
							.clip(shape = RoundedCornerShape(12.dp))
							.background(Color.White)
							.height(rowHeight)
							.width(columnHeight),
						horizontalAlignment = Alignment.CenterHorizontally,
						verticalArrangement = Arrangement.Center
					){
						if (i == 1){
							when(j){
								1 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(R.drawable.clock), contentDescription = "")
								2 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(R.drawable.wave), contentDescription = "")
								3 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(R.drawable.stopwatch), contentDescription = "")
								4 -> Icon(modifier = Modifier. height(30.dp), painter = painterResource(R.drawable.compass), contentDescription = "")

							}
						} else if (j == 1){
							Text((i-1).toString() + ":00")
						} else {
							when(j){
								2 -> Text("1m")
								3 -> Text("5s")
								4 -> Text("NW")
							}
						}
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(innerPadding: PaddingValues, navController: NavHostController){
	val scrollState = rememberScrollState()
	var isActive = remember { mutableStateOf(0) }
	val scope = rememberCoroutineScope()


	Column(modifier = Modifier
		.fillMaxWidth()
		.fillMaxHeight()
		.background(colorResource(R.color.offWhite))
		.padding(innerPadding)
		.verticalScroll(scrollState),
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		Column(){
			SearchBar(
				adjustablePadding = 10.dp,
				onClick = { value ->
					scope.launch {
						val result = GeoServices.fetchGeoData(
							location = value.value,
							key = BuildConfig.GEO_KEY
						)
						println("Printing Result...")
						println(result)
					}
					//navController.navigate(SearchScreen.route)
				})
			DayCardRow(adjustablePadding = 20.dp)
		}
		Column(modifier = Modifier
			.fillMaxWidth()) {
			Row(
				modifier = Modifier
					.padding(5.dp)
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				TabButton(
					text = "Waves",
					width = 100.dp,
					drawable = R.drawable.wave,
					active = isActive.value == 0,
					fontSize = 15.sp,
					onClick = {
						isActive.value = 0
					}
				)
				TabButton(
					text = "Wind",
					width = 100.dp,
					drawable = R.drawable.wind,
					active = isActive.value == 1,
					fontSize = 15.sp,
					onClick = {
						isActive.value = 1
					}
				)
				TabButton(
					text = "Weather",
					width = 100.dp,
					drawable = R.drawable.sun,
					active = isActive.value == 2,
					fontSize = 15.sp,
					onClick = {
						isActive.value = 2
					}
				)
			}
			Table()

		}
	}
}


// Previews
@Preview(showBackground = true)
@Composable
fun PreviewSearchBar(){
	SurfistaTheme {
		SearchBar(adjustablePadding = 10.dp)
	}
}

@Preview(showBackground = true)
@Composable
fun PreviewDayCard(){
	SurfistaTheme {
		DayCard()
	}
}


@Preview(showBackground = true)
@Composable
fun PreviewDashboard(){
	Scaffold(
		bottomBar = {
			BottomAppBar(containerColor = colorResource(R.color.offWhite)) {
				NavigationBar()
			}
		}
	) { innerPadding ->
		Dashboard(innerPadding, navController = rememberNavController())
	}
}