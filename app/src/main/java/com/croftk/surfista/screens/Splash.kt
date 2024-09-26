package com.croftk.surfista.screens
import android.media.Image
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.LoginScreen
import kotlinx.coroutines.delay


@Composable
fun Splash(innerPadding: PaddingValues, navController: NavController, db: AppDatabase){
	val scale = remember {
		Animatable(0f)
	}

	LaunchedEffect(key1 = true) {
		scale.animateTo(
			targetValue = 0.3f,
			animationSpec = tween(
				durationMillis = 500,
				easing = {
					OvershootInterpolator(2f).getInterpolation(it)
				}
			)
		)
		delay(3000L)

		val user = db.userDao().getAll()

		if (user.isNotEmpty()){
			val isLoggedIn = user[0].loggedIn
			if (isLoggedIn){
				navController.navigate(DashboardScreen.route)
			} else {
				navController.navigate(LoginScreen.route)
			}
		} else {
			navController.navigate(LoginScreen.route)
		}

	}

	Column(
		modifier = Modifier.fillMaxWidth().fillMaxHeight().background(colorResource(R.color.offWhite)),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		Box(
			modifier = Modifier
				.clip(shape = RoundedCornerShape(12.dp))
				.background(colorResource(R.color.blue))
				.padding(24.dp)
		) {
			Image(
				modifier = Modifier.height(120.dp),
				painter = painterResource(R.drawable.surfboard),
				contentDescription = ""
			)
		}
	}
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewSplash(){
//	Scaffold(
//	) { innerPadding ->
//		Splash(innerPadding, navController = rememberNavController())
//	}
//}