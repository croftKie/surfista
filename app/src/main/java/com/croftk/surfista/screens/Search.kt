package com.croftk.surfista.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.components.SearchResultModifier
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.Helpers
import com.croftk.surfista.utilities.httpServices.WaveServices
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


@Composable
fun Search(innerPadding: PaddingValues, navController: NavController, db: AppDatabase) {

	val geoData = db.GeolocationDao().getAllSorted()
	val selectedLocation = remember { mutableIntStateOf(-1) }
	val openDialog = remember { mutableStateOf(true) }
	Column(
		modifier = Modifier
			.fillMaxHeight()
			.padding(innerPadding)
			.background(colorResource(R.color.offWhite))
	) {
		SearchBar(onClick = { value ->
			println(value.value)
		})
		SearchResults(geoData, selectedLocation, openDialog, db, navController)
	}
}

@Composable
fun SearchResults(
	geoData: List<GeoLocation>,
	selectedLocation: MutableIntState,
	openDialog: MutableState<Boolean>,
	db: AppDatabase,
	navController: NavController
){
	val scrollState = rememberScrollState()
	Column(
		Modifier
			.fillMaxWidth()
			.padding(vertical = 12.dp)
			.verticalScroll(scrollState),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		geoData.forEachIndexed {index, item ->
			SearchResult(item, index, selectedLocation, openDialog, db, navController)
		}
	}
}

@Composable
fun SearchResult(
	item: GeoLocation,
	index: Int,
	selectedLocation: MutableIntState,
	openDialog: MutableState<Boolean>,
	db: AppDatabase,
	navController: NavController
){
	val scope = rememberCoroutineScope()
	Row(
		modifier = SearchResultModifier()
			.clickable {
				scope.launch {
					db.MarineDao().deleteAll()
					db.TempDao().deleteAll()
					db.WindDao().deleteAll()

					val waveResult = WaveServices.fetchWaveData(item.lat, item.lon)
					if(waveResult != null){
						Helpers.storeFetchedData(waveResult, item, db.MarineDao())
					}

					val tempResult = WaveServices.fetchTempData(item.lat, item.lon)

					if(tempResult != null){
						Helpers.storeFetchedData(tempResult, item, db.TempDao())
					}

					val windResult = WaveServices.fetchWindData(item.lat, item.lon)
					if(windResult != null){
						Helpers.storeFetchedData(windResult, item, db.WindDao())
					}

					navController.navigate(DashboardScreen.route)
				}
			}
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight(),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center){
				MapModal(item.lat.toDouble(), item.lon.toDouble())
			}
			Column(modifier = Modifier.fillMaxWidth()) {
				Text(text = item.name, modifier = Modifier.fillMaxWidth())
				Text(text = item.importance.toString())
			}
		}
	}
}

@Composable
fun MapModal(lat: Double, lon: Double){
	Column(Modifier
		.width(75.dp)
		.height(75.dp)
		.clip(RoundedCornerShape(6.dp))
		.clipToBounds()
	) {
		OsmdroidMapView(lat, lon)
	}
}

@Composable
fun OsmdroidMapView(lat: Double, lon: Double) {
	var geoPoint by remember{ mutableStateOf(GeoPoint(lat,lon))}
	AndroidView(
		factory = { context ->
			// Creates the view
			MapView(context).apply {
				setMultiTouchControls(false)
				setZoomLevel(13.0)
				setTileSource(TileSourceFactory.OpenTopo)
			}
		},
		update = { view ->
			view.controller.setCenter(geoPoint)
		}
	)
}
