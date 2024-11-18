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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import com.croftk.surfista.R
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.Board
import com.croftk.surfista.db.entities.Favourite
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.screens.MapModal
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.Helpers
import com.croftk.surfista.utilities.TextHelpers
import com.croftk.surfista.utilities.httpServices.WaveServices
import kotlinx.coroutines.launch

@Composable
fun HorizontalCard(
	item: GeoLocation,
	index: Int,
	selectedLocation: MutableIntState,
	openDialog: MutableState<Boolean>,
	db: AppDatabase,
	navController: NavController,
	favData: MutableState<List<Favourite>>,
	radius: Dp = 12.dp,
	height: Dp = 135.dp,
){
	val scope = rememberCoroutineScope()
	val match = "%.0f".format((item.importance * 100))

	val isFavourite = remember { mutableStateOf(db.FavouriteDao().checkIfInFavourites(item.placeId).size == 1) }


	Row(modifier = Modifier
		.height(height)
		.fillMaxWidth()
		.background(colorResource(R.color.offWhite)),
		horizontalArrangement = Arrangement.spacedBy(6.dp)
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.clip(RoundedCornerShape(radius))
				.clickable {
					scope.launch {
						val waveResult = WaveServices.fetchWaveData(item.lat, item.lon)
						val tempResult = WaveServices.fetchTempData(item.lat, item.lon)
						val windResult = WaveServices.fetchWindData(item.lat, item.lon)

						if (waveResult?.hourly?.wave_height?.get(0) == null){
							println("No Wave Data Available")
						} else {
							db.MarineDao().deleteAll()
							db.TempDao().deleteAll()
							db.WindDao().deleteAll()

							if(waveResult != null){
								Helpers.storeFetchedData(waveResult, item, db.MarineDao())
							}
							if(tempResult != null){
								Helpers.storeFetchedData(tempResult, item, db.TempDao())
							}
							if(windResult != null){
								Helpers.storeFetchedData(windResult, item, db.WindDao())
							}
							navController.navigate(DashboardScreen.route)
						}
					}
				},
			border = BorderStroke(1.dp, colorResource(R.color.borderDark))
		){
			Row(
				modifier = Modifier
					.fillMaxHeight()
					.fillMaxWidth()
					.background(colorResource(R.color.lightGrey)),
				verticalAlignment = Alignment.Bottom,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Column(modifier = Modifier
					.fillMaxHeight(),
					verticalArrangement = Arrangement.Center
				){
					MapModal(item.lat.toDouble(), item.lon.toDouble())
				}
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.fillMaxWidth(0.75f)
						.padding(12.dp),
					verticalArrangement = Arrangement.SpaceBetween
				) {
					Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text="${match}% Match", fontSize = 12.sp)
						}
					}
					Column {
						TextHelpers.createAddress(item.name).forEach{ it ->
							Text(color = colorResource(R.color.darkGray), text=it, fontSize = 18. sp)
						}
					}
				}
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.padding(8.dp),
					verticalArrangement = Arrangement.Bottom
				)  {
					ClickableIcon(
						modifier = Modifier.height(30.dp),
						drawableImage = if(isFavourite.value) R.drawable.heartfilled else R.drawable.heartempty,
						contentDesc = R.string.search_mag_desc,
						click = {
							if(isFavourite.value){
								db.FavouriteDao().deleteFavourite(item.placeId)
								isFavourite.value = false
							} else {
								val favourite = Favourite(
									placeId = item.placeId,
									name = item.name,
									lat = item.lat,
									lon = item.lon,
									importance = item.importance
								)
								db.FavouriteDao().insertFavourite(favourite)
								isFavourite.value = true
							}

							val newData = db.FavouriteDao().getAllSorted()
							favData.value = newData
						}
					)
				}
			}
		}
	}
}
@Composable
fun HorizontalCard(
	item: Favourite,
	index: Int,
	selectedLocation: MutableIntState,
	openDialog: MutableState<Boolean>,
	db: AppDatabase,
	navController: NavController,
	favData: MutableState<List<Favourite>>,
	radius: Dp = 12.dp,
	height: Dp = 135.dp,
){
	val scope = rememberCoroutineScope()
	val match = "%.0f".format((item.importance * 100))



	Row(modifier = Modifier
		.height(height)
		.fillMaxWidth()
		.clip(RoundedCornerShape(radius))
		.background(colorResource(R.color.offWhite)),
		horizontalArrangement = Arrangement.spacedBy(6.dp)
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
				.clip(RoundedCornerShape(radius))
				.clickable {
					scope.launch {
						val waveResult = WaveServices.fetchWaveData(item.lat, item.lon)
						val tempResult = WaveServices.fetchTempData(item.lat, item.lon)
						val windResult = WaveServices.fetchWindData(item.lat, item.lon)

						if (waveResult?.hourly?.wave_height?.get(0) == null){
							println("No Wave Data Available")
						} else {
							db.MarineDao().deleteAll()
							db.TempDao().deleteAll()
							db.WindDao().deleteAll()

							if(waveResult != null){
								Helpers.storeFavouriteFetchedData(waveResult, item, db.MarineDao())
							}
							if(tempResult != null){
								Helpers.storeFavouriteFetchedData(tempResult, item, db.TempDao())
							}
							if(windResult != null){
								Helpers.storeFavouriteFetchedData(windResult, item, db.WindDao())
							}
							navController.navigate(DashboardScreen.route)
						}
					}
				},
			border = BorderStroke(1.dp, colorResource(R.color.borderDark))
		){
			Row(
				modifier = Modifier
					.fillMaxHeight()
					.fillMaxWidth()
					.background(colorResource(R.color.lightGrey)),
				verticalAlignment = Alignment.Bottom,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Column(modifier = Modifier
					.fillMaxHeight(),
					verticalArrangement = Arrangement.Center
				){
					MapModal(item.lat.toDouble(), item.lon.toDouble())
				}
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.fillMaxWidth(0.75f)
						.padding(12.dp),
					verticalArrangement = Arrangement.SpaceBetween
				) {
					Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
						Box(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.background(colorResource(R.color.lightTurq))
								.padding(vertical = 4.dp, horizontal = 8.dp)

						) {
							Text(text="${match}% Match", fontSize = 12.sp)
						}
					}
					Column {
						TextHelpers.createAddress(item.name).forEach{ it ->
							Text(color = colorResource(R.color.darkGray), text=it, fontSize = 18. sp)
						}
					}
				}
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.padding(8.dp),
					verticalArrangement = Arrangement.Bottom
				)  {
					ClickableIcon(
						modifier = Modifier.height(30.dp),
						drawableImage = R.drawable.trash,
						contentDesc = R.string.search_mag_desc,
						click = {
							db.FavouriteDao().deleteFavourite(item.placeId)
							favData.value = db.FavouriteDao().getAll()
						}
					)
				}
			}
		}
	}
}


//@Preview
//@Composable
//fun PrevHoriCard(){
//	Surface(Modifier.background(colorResource(R.color.offWhite)).padding(12.dp)) {
//		HorizontalCard()
//	}
//}





