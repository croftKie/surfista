package com.croftk.surfista.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.ClickableIcon
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.components.TitleBar

@Composable
fun BoardCardRow(adjustablePadding: Dp){
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
				"My Boards",
				fontSize = 30.sp,
			)
			Text("Your current quiver of surfboards")
		}
		Row(modifier = Modifier
			.padding(bottom = adjustablePadding, start = adjustablePadding / 2)
			.horizontalScroll(scrollState),
			horizontalArrangement = Arrangement.spacedBy(12.dp)) {
			for (i in 1..7){
				BoardCard()
			}
		}
	}
}

@Composable
fun BoardCard(){
	Column(modifier = Modifier
		.clip(shape = RoundedCornerShape(12.dp))
		.background(colorResource(R.color.white))
		.height(150.dp)
		.width(300.dp)
		.padding(6.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth().padding(6.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Image(
				modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f),
				painter = painterResource(R.drawable.surfboard),
				contentDescription = "",
			)
			Column(
				modifier = Modifier.fillMaxWidth().fillMaxHeight(),
				horizontalAlignment = Alignment.End,
				verticalArrangement = Arrangement.SpaceBetween) {
				Text(text = "Board Brand", fontSize = 25.sp)
				Text(text = "9ft", fontSize = 25.sp)
				Row(
					modifier = Modifier.padding(6.dp),
					horizontalArrangement = Arrangement.spacedBy(32.dp)
				) {
					ClickableIcon(
						modifier = Modifier.height(25.dp),
						drawableImage = R.drawable.edit,
						contentDesc = R.string.search_mag_desc,
						click = {}
					)
					ClickableIcon(
						modifier = Modifier.height(25.dp),
						drawableImage = R.drawable.trash,
						contentDesc = R.string.search_mag_desc,
						click = {}
					)
				}
			}
		}

	}
}


@Composable
fun Quiver(innerPadding: PaddingValues, navController: NavController){
	val vertScrollState = rememberScrollState()
	Column(
		modifier = Modifier.fillMaxHeight().padding(innerPadding).fillMaxWidth().background(colorResource(R.color.offWhite)),
		horizontalAlignment = Alignment.CenterHorizontally
	){
		BoardCardRow(12.dp)
		SearchBar(12.dp)
		Column(
			modifier = Modifier
				.padding(12.dp)
				.verticalScroll(vertScrollState)
				.fillMaxWidth()
		) {
			for (i in 1..10){
				SearchResult()
			}
		}
	}
}

@Composable
fun SearchResult(){
	Row(
		modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
		horizontalArrangement = Arrangement.SpaceAround,
		verticalAlignment = Alignment.CenterVertically
	) {
		ImageIcon(
			modifier = Modifier.height(40.dp),
			drawableImage = R.drawable.surfboard,
			contentDesc = R.string.search_mag_desc
		)
		Column(modifier = Modifier.fillMaxWidth(0.5f)) {
			Text("Board Brand", fontSize = 30.sp)
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text("Longboard", fontSize = 20.sp)
				Text("9.1ft", fontSize = 20.sp)
			}
		}
		ClickableIcon(
			modifier = Modifier.height(30.dp),
			drawableImage = R.drawable.add,
			contentDesc = R.string.search_mag_desc,
			click = {}
		)
	}
}


@Preview
@Composable
fun PreviewBoardCard(){
	BoardCard()
}


@Preview
@Composable
fun PreviewQuiver(){
	Scaffold(
		bottomBar = {
			BottomAppBar {
				NavigationBar()
			}
		}
	) { innerPadding ->
		Quiver(innerPadding, navController = rememberNavController())
	}
}