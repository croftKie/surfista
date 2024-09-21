package com.croftk.surfista.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.httpServices.WaveServices
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(innerPadding: PaddingValues, navController: NavController) {

	val sheetState = rememberModalBottomSheetState(
		skipPartiallyExpanded = false
	)
	var showBottomSheet = remember { mutableStateOf(false) }

	Column(
		modifier = Modifier
			.fillMaxHeight()
			.padding(innerPadding)
			.background(colorResource(R.color.offWhite))
	) {
		SearchBar(onClick = { showBottomSheet.value = true })
		SearchTutorial()
		BootySheet(navController)
	}
}

@Composable
fun SearchTutorial(){
	Column(
		modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
		verticalArrangement = Arrangement.spacedBy(32.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			modifier = Modifier.clip(RoundedCornerShape(12.dp)).height(80.dp).background(colorResource(R.color.blue)).padding(12.dp),
			painter = painterResource(R.drawable.mag),
			contentDescription = ""
		)
		Text("1. Search a location above")
		Text("2. Drop a pin in the map")
		Text("3. Head to the dashboard")
	}
}

@Composable
fun MapModal(navController: NavController){
	val scope = rememberCoroutineScope()
	Column(Modifier.fillMaxWidth().height(350.dp), horizontalAlignment = Alignment.CenterHorizontally) {
		Box(modifier = Modifier
			.padding(64.dp)
			.fillMaxWidth()
			.fillMaxHeight()
			.clickable {
				navController.navigate(DashboardScreen.route)
				scope.launch {
					val result = WaveServices.fetchWaveData()
					println(result)
				}
			}
		){
			OsmdroidMapView()
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BootySheet(navController: NavController) {
	val scope = rememberCoroutineScope()
	val scaffoldState = rememberBottomSheetScaffoldState()

	BottomSheetScaffold(
		scaffoldState = scaffoldState,
		sheetPeekHeight = 400.dp,
		sheetSwipeEnabled = false,
		sheetContent = {
			Box(
				modifier = Modifier.fillMaxSize().padding(32.dp),
				contentAlignment = Alignment.Center
			) {
				MapModal(navController)
			}
		}
	) {

	}
}


@Composable
fun OsmdroidMapView() {
	var geoPoint by remember{ mutableStateOf(GeoPoint(0.0,0.0))}

	AndroidView(
		modifier = Modifier.fillMaxSize(),
		factory = { context ->
			// Creates the view
			MapView(context).apply {
				setZoomLevel(9.0)
				setTileSource(TileSourceFactory.USGS_TOPO)
				setOnClickListener {
					TODO("Handle click here")
				}
			}
		},
		update = { view ->
			// Code to update or recompose the view goes here
			// Since geoPoint is read here, the view will recompose whenever it is updated
			view.controller.setCenter(geoPoint)
		}
	)
}


@Preview(showBackground = true)
@Composable
fun PreviewSearch(){
	Scaffold(
		bottomBar = {
//			BottomAppBar {
//				NavigationBar()
//			}
		}
	) { innerPadding ->
		Search(innerPadding, navController = rememberNavController())
	}
}