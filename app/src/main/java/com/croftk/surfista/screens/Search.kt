package com.croftk.surfista.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.croftk.surfista.BuildConfig
import com.croftk.surfista.R
import com.croftk.surfista.components.HorizontalCard
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.components.SearchResultModifier
import com.croftk.surfista.components.VerticalDashboardCard
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.Favourite
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.Helpers
import com.croftk.surfista.utilities.SearchScreen
import com.croftk.surfista.utilities.httpServices.GeoServices
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
	val scope = rememberCoroutineScope()
	val geoData by remember { mutableStateOf(db.GeolocationDao().getAllSorted()) }
	val favData = remember { mutableStateOf(db.FavouriteDao().getAll()) }
	val selectedLocation = remember { mutableIntStateOf(-1) }
	val openDialog = remember { mutableStateOf(true) }
	val searchInput = remember { mutableStateOf("") }
	Column(
		modifier = Modifier
			.fillMaxHeight()
			.fillMaxWidth()
			.background(colorResource(R.color.grenTurq))
			.padding(innerPadding),
		horizontalAlignment = Alignment.CenterHorizontally
	){
		Column(modifier = Modifier.height(200.dp)) {
			val state = rememberLazyListState()

			LazyRow(modifier = Modifier
				.height(250.dp)
				.padding(bottom = 6.dp, start = 6.dp / 2),
				state = state,
				flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(12.dp))
			{
				items(favData.value.size) { it ->
					HorizontalCard(
						favData.value[it],
						it,
						selectedLocation,
						openDialog,
						db,
						navController,
						favData
					)
				}
			}
		}

		Column(
			modifier = Modifier
				// .verticalScroll(vertScrollState)
				.fillMaxWidth()
				.clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
				.background(colorResource(R.color.offWhite))
				.padding(12.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			SearchBar(
				12.dp,
				value = searchInput,
				buttonActive = true,
				placeholder = "Search for a surf spot",
				onClick = { value ->
					scope.launch {
						val result = GeoServices.fetchGeoData(
							location = value.value
						)
						db.GeolocationDao().deleteAll()
						result?.forEach { item ->
							db.GeolocationDao().insertLocation(GeoLocation(
								placeId = item.place_id,
								name = Helpers.cleanGeoAddress(item.display_name),
								lat = item.lat,
								lon = item.lon,
								importance = item.importance.toFloat()
							))
						}
						navController.navigate(SearchScreen.route)
					}
				}
			)
			SearchResults(geoData, selectedLocation, openDialog, db, navController, favData)
		}
	}
}

@Composable
fun SearchResults(
	geoData: List<GeoLocation>,
	selectedLocation: MutableIntState,
	openDialog: MutableState<Boolean>,
	db: AppDatabase,
	navController: NavController,
	favData: MutableState<List<Favourite>>
){
	val scrollState = rememberScrollState()
	Column(
		Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.padding(top = 12.dp)
			.verticalScroll(scrollState)
			.clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
			.background(colorResource(R.color.offWhite)),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		geoData.forEachIndexed {index, item ->
			HorizontalCard(item, index, selectedLocation, openDialog, db, navController, favData)
		}
	}
}

@Composable
fun MapModal(lat: Double, lon: Double){
	Column(Modifier
		.width(125.dp)
		.fillMaxHeight()
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
