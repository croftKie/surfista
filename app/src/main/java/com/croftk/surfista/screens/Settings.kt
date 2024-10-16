package com.croftk.surfista.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.TabButton
import com.croftk.surfista.components.TitleBar
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.User
import com.croftk.surfista.utilities.LoginScreen


@Composable
fun ListItem(
	modifier: Modifier,
	itemText: String,
	color: Color,
	backgroundColor: Color,
	image: Int = R.drawable.downarrow,
){
	Column(modifier = Modifier.background(backgroundColor)){
		Row(
			modifier = modifier.padding(12.dp).fillMaxWidth(0.9f),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(itemText, fontSize = 25.sp)
			ImageIcon(Modifier.height(20.dp), image, R.string.search_mag_desc)
		}
	}
}

@Composable
fun Profile(db: AppDatabase, navController: NavController){
	val user = db.userDao().getAll()[0]
	Column(
		modifier = Modifier.fillMaxHeight(0.3f),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			modifier = Modifier
				.height(100.dp)
				.clip(shape = CircleShape)
				.background(colorResource(R.color.blue))
				.padding(12.dp),
			painter = painterResource(R.drawable.wave),
			contentDescription = ""
		)
		Text(user.email)
		TabButton(text = "Logout", iconActive = false) {
			db.userDao().updateUser(
				User(
					uid = user.uid,
					email = user.email,
					password = user.password,
					loggedIn = false
				)
			)
			navController.navigate(LoginScreen.route)
		}
	}
}

@Composable
fun Settings(innerPadding: PaddingValues, navController: NavController, db: AppDatabase){
	Column(
		modifier = Modifier
			.background(colorResource(R.color.offWhite))
			.padding(innerPadding)
			.fillMaxWidth()
			.fillMaxHeight(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Profile(db, navController)
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.padding(18.dp),
			verticalArrangement = Arrangement.spacedBy(48.dp)
		) {
			Column(
				modifier = Modifier
					.clip(RoundedCornerShape(18.dp))
					.background(colorResource(R.color.white)),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				ListItem(
					Modifier,
					"Change Email",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.mail
				)
				HorizontalDivider(Modifier.fillMaxWidth(0.8f))
				ListItem(
					Modifier,
					"Change Password",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.padlock
				)
				HorizontalDivider(Modifier.fillMaxWidth(0.8f))
				ListItem(
					Modifier,
					"Toggle Notifications",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.notification
				)
			}
			Column(
				modifier = Modifier
					.clip(RoundedCornerShape(18.dp))
					.background(colorResource(R.color.white)),
				horizontalAlignment = Alignment.CenterHorizontally
			){
				ListItem(
					Modifier,
					"Privacy Policy",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.googledocs
				)
				HorizontalDivider(Modifier.fillMaxWidth(0.8f))
				ListItem(
					Modifier,
					"Delete Account",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.trash
				)
			}
		}
	}
}


//@Composable
//@Preview
//fun PreviewSettings(){
//	Scaffold(
//		bottomBar = {
//			BottomAppBar {
//				NavigationBar()
//			}
//		}
//	) { innerPadding ->
//		Settings(innerPadding, navController = rememberNavController())
//	}
//}