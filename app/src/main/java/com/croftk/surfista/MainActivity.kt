package com.croftk.surfista

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.croftk.surfista.components.ClickableIcon
import com.croftk.surfista.screens.Dashboard
import com.croftk.surfista.screens.Login
import com.croftk.surfista.screens.Quiver
import com.croftk.surfista.screens.Search
import com.croftk.surfista.screens.Settings
import com.croftk.surfista.screens.Splash
import com.croftk.surfista.ui.theme.SurfistaTheme
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.LoginScreen
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.User
import com.croftk.surfista.utilities.QuiverScreen
import com.croftk.surfista.utilities.SearchScreen
import com.croftk.surfista.utilities.SettingsScreen
import com.croftk.surfista.utilities.SplashScreen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()

		val db = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java, "surfista-db"
		).allowMainThreadQueries().fallbackToDestructiveMigration().build()

		setContent {
			SurfistaTheme {
				Navigation(db)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(db: AppDatabase){

	val navController = rememberNavController()
	val topBarState = rememberSaveable { (mutableStateOf(true)) }
	val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
	val navBackStackEntry by navController.currentBackStackEntryAsState()

	when (navBackStackEntry?.destination?.route){
		LoginScreen.route -> {
			topBarState.value = false
			bottomBarState.value = false
		}
		SplashScreen.route -> {
			topBarState.value = false
			bottomBarState.value = false
		}
		DashboardScreen.route -> {
			topBarState.value = false
			bottomBarState.value = true
		}
		else -> {
			topBarState.value = true
			bottomBarState.value = true
		}
	}

	Scaffold(
		topBar = {
			if (topBarState.value){
				TopAppBar(
					modifier = Modifier.height(80.dp),
					title = {},
					navigationIcon = {
						ClickableIcon(
							Modifier
								.height(30.dp)
								.padding(start = 6.dp, bottom = 6.dp),
							R.drawable.arrow,
							R.string.search_mag_desc
						){
							navController.popBackStack()
						}
					},
					colors = TopAppBarColors(
						containerColor = colorResource(R.color.offWhite),
						titleContentColor = Color.Black,
						actionIconContentColor = Color.Black,
						navigationIconContentColor = Color.Black,
						scrolledContainerColor = Color.Black
					)
				)
			}
		},
		bottomBar = {
			if(bottomBarState.value){
				BottomAppBar(
					modifier = Modifier.height(80.dp),
					containerColor = colorResource(R.color.offWhite)
				) {
					NavigationBar(
						leftOptionOnClick = {
							navController.navigate(QuiverScreen.route)
						},
						centerOptionOnClick = {
							navController.navigate(DashboardScreen.route)
						},
						rightOptionOnClick = {
							navController.navigate(SettingsScreen.route)
						}
					)
				}
			}
		}
	) { innerPadding ->
		NavHost(
			navController,
			startDestination = SplashScreen.route
		){
			composable(DashboardScreen.route) {
				Dashboard(innerPadding, navController, db)
			}
			composable(SettingsScreen.route) {
				Settings(innerPadding, navController)
			}
			composable(QuiverScreen.route) {
				Quiver(innerPadding, navController)
			}
			composable(LoginScreen.route) {
				Login(innerPadding, navController)
			}
			composable(SearchScreen.route) {
				Search(innerPadding, navController, db)
			}
			composable(SplashScreen.route) {
				Splash(innerPadding, navController)
			}
		}
	}
}