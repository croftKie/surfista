package com.croftk.surfista

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
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
import com.croftk.surfista.utilities.QuiverScreen
import com.croftk.surfista.utilities.SearchScreen
import com.croftk.surfista.utilities.SettingsScreen
import com.croftk.surfista.utilities.SplashScreen
import com.croftk.surfista.utilities.animations.Enter
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
	private lateinit var auth: FirebaseAuth

	private fun createAccount(
		email: String,
		password: String,
		navController: NavController,
		onSuccess: ()->Unit
	){
		if(email.isEmpty() || password.isEmpty()){
			Toast.makeText(baseContext, "Empty Input", Toast.LENGTH_SHORT).show()
		} else {
			auth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this) { task ->
					if (task.isSuccessful) {
						Log.d(TAG, "createUserWithEmail:success")
						Toast.makeText(
							baseContext,
							"Sign up complete!",
							Toast.LENGTH_SHORT,
						).show()
						onSuccess()
					} else {
						Log.w(TAG, "createUserWithEmail:failure", task.exception)
						// If sign in fails, display a message to the user.
						Toast.makeText(
							baseContext,
							"Authentication failed, try again.",
							Toast.LENGTH_SHORT,
						).show()
					}
				}
		}
	}
	private fun signInWithEmailAndPassword(
		email: String,
		password: String,
		navController: NavController,
		onSuccess: () -> Unit
	){
		if(email.isEmpty() || password.isEmpty()){
			Toast.makeText(baseContext, "Empty Input", Toast.LENGTH_SHORT).show()
		} else {
			auth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this) { task ->
					if (task.isSuccessful) {
						// Sign in success, update UI with the signed-in user's information
						Log.d(TAG, "signInWithEmail:success")
						onSuccess()
					} else {
						// If sign in fails, display a message to the user.
						Log.w(TAG, "signInWithEmail:failure", task.exception)
						Toast.makeText(
							baseContext,
							"Authentication failed, try again.",
							Toast.LENGTH_SHORT,
						).show()
					}
				}
		}
	}



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		auth = Firebase.auth
		enableEdgeToEdge()

	}
	override fun onStart(){
		super.onStart()
		val db = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java, "surfista-db"
		).allowMainThreadQueries().fallbackToDestructiveMigration().build()

		setContent {
			SurfistaTheme {
				Navigation(db, auth, this::createAccount, this::signInWithEmailAndPassword)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
	db: AppDatabase,
	auth: FirebaseAuth,
	createAccount: (String, String, NavController, ()->Unit)->Unit,
	signInWithEmailAndPassword: (String, String, NavController, ()->Unit)->Unit
	){
	val currentUser = auth.currentUser
	val navController = rememberNavController()
	val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
	val actionButtonState = rememberSaveable { (mutableStateOf(false)) }
	val navBackStackEntry by navController.currentBackStackEntryAsState()

	when (navBackStackEntry?.destination?.route){
		LoginScreen.route -> {
			actionButtonState.value = false
			bottomBarState.value = false
		}
		SplashScreen.route -> {
			actionButtonState.value = false
			bottomBarState.value = false
		}
		DashboardScreen.route -> {
			actionButtonState.value = false
			bottomBarState.value = true
		}
		SearchScreen.route -> {
			actionButtonState.value = true
		}
		else -> {
			actionButtonState.value = false
			bottomBarState.value = true
		}
	}

	Scaffold(
		bottomBar = {
			if(bottomBarState.value){
				BottomAppBar(
					modifier = Modifier.height(80.dp),
					containerColor = colorResource(R.color.offWhite),
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
			startDestination = if(currentUser != null) DashboardScreen.route else SplashScreen.route
		){
			composable(DashboardScreen.route) {
				Enter {
					Dashboard(innerPadding, navController, db)
				}
			}
			composable(SettingsScreen.route) {
				Enter {
					Settings(innerPadding, navController, db, currentUser, auth)
				}
			}
			composable(QuiverScreen.route) {
				Enter {
					Quiver(innerPadding, navController, db)
				}
			}
			composable(LoginScreen.route) {
				Enter {
					Login(
						innerPadding,
						navController,
						db,
						auth,
						createAccount,
						signInWithEmailAndPassword)
				}
			}
			composable(SearchScreen.route) {
				Enter{
					Search(innerPadding, navController, db)
				}
			}
			composable(SplashScreen.route) {
				Enter {
					Splash(innerPadding, navController, db)
				}
			}
		}
	}
}