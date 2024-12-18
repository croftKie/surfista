package com.croftk.surfista.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.croftk.surfista.components.Popup
import com.croftk.surfista.components.TabButton
import com.croftk.surfista.components.TitleBar
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.User
import com.croftk.surfista.utilities.LoginScreen
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun ListItem(
	modifier: Modifier,
	itemText: String,
	color: Color,
	backgroundColor: Color,
	image: Int = R.drawable.downarrow,
	click: () -> Unit = {}
){
	Column(
		modifier = Modifier.background(backgroundColor).clickable {
			click()
		}
	){
		Row(
			modifier = modifier.padding(12.dp).fillMaxWidth(0.75f),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(itemText, fontSize = 18.sp)
			ImageIcon(Modifier.height(18.dp), image, R.string.search_mag_desc)
		}
	}
}

@Composable
fun Profile(db: AppDatabase, navController: NavController, user: FirebaseUser?, auth: FirebaseAuth){
	Column(
		modifier = Modifier.fillMaxHeight(0.30f).padding(top = 16.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			modifier = Modifier
				.height(150.dp)
				.clip(shape = CircleShape),
			painter = painterResource(R.drawable.logo),
			contentDescription = ""
		)
		user?.email?.let { Text(it, fontSize = 25.sp) }
	}
}

@Composable
fun Settings(
	innerPadding: PaddingValues,
	navController: NavController,
	db: AppDatabase,
	user: FirebaseUser?,
	auth: FirebaseAuth
){
	val openAlertDialog = remember { mutableStateOf(false) }
	val dialogTitle = remember { mutableStateOf("") }
	val dialogText = remember { mutableStateOf("") }
	val icon = remember { mutableIntStateOf(R.drawable.sun) }
	val onConfirmation = remember { mutableStateOf<(String)->Unit>({}) }
	val onDismiss = remember { mutableStateOf({}) }
	val mode = remember { mutableIntStateOf(0) }
	val placeholder = remember { mutableStateOf("") }

	fun onConf():(String)->Unit{
		return onConfirmation.value
	}

	when {
		openAlertDialog.value -> {
			Popup(
				onConfirmation = onConf(),
				onDismissRequest = {
					openAlertDialog.value = false
					onDismiss.value()
				},
				dialogText = dialogText.value,
				dialogTitle = dialogTitle.value,
				icon = icon.intValue,
				mode = mode.intValue,
				placeholderText = placeholder.value
			)
		}
	}


	Column(
		modifier = Modifier
			.background(colorResource(R.color.grenTurq))
			.padding(innerPadding)
			.fillMaxWidth()
			.fillMaxHeight(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Profile(db, navController, user, auth)
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.fillMaxWidth()
				.clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
				.background(colorResource(R.color.offWhite))
				.padding(top = 16.dp),
			verticalArrangement = Arrangement.spacedBy(48.dp),
			horizontalAlignment = Alignment.CenterHorizontally
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
				HorizontalDivider(Modifier.fillMaxWidth(0.75f))
				ListItem(
					Modifier,
					"Change Password",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.padlock
				)
//				HorizontalDivider(Modifier.fillMaxWidth(0.75f))
//				ListItem(
//					Modifier,
//					"Toggle Notifications",
//					Color.DarkGray,
//					Color.Transparent,
//					R.drawable.notification
//				)
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
				HorizontalDivider(Modifier.fillMaxWidth(0.75f))
				ListItem(
					Modifier,
					"Log Out",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.logout,
					click = {
						auth.signOut()
						navController.navigate(LoginScreen.route)
					}
				)
				HorizontalDivider(Modifier.fillMaxWidth(0.75f))
				ListItem(
					Modifier,
					"Delete Account",
					Color.DarkGray,
					Color.Transparent,
					R.drawable.trash,
					click = {
						dialogTitle.value = "Delete Account"
						dialogText.value = "Are you sure that you want to delete your account?"
						icon.intValue = R.drawable.trash
						placeholder.value = "Enter Password"
						openAlertDialog.value = true

						onConfirmation.value = { strValue ->
							if(mode.intValue == 0){
								mode.intValue = 1
								dialogText.value = "Confirm password to delete your account immediately."
							} else if (mode.intValue == 1){
								val creds = EmailAuthProvider.getCredential(auth.currentUser?.email!!, strValue)

								auth.currentUser?.reauthenticate(creds)?.addOnCompleteListener {
									auth.currentUser?.delete()?.addOnSuccessListener {
										dialogText.value = "Account Deleted!"
									}?.addOnFailureListener { error ->
										println(error)
									}
								}
							}
						}
					}
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