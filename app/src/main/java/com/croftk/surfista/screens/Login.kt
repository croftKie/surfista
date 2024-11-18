package com.croftk.surfista.screens

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.croftk.surfista.R
import com.croftk.surfista.components.InputField
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.utilities.DashboardScreen
import com.croftk.surfista.utilities.animations.moveElement
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Login(
	innerPadding: PaddingValues,
	navController: NavController,
	db: AppDatabase,
	auth: FirebaseAuth,
	createAccount: (String, String, NavController, ()->Unit) -> Unit,
	signInWithEmailAndPassword: (String, String, NavController, ()->Unit)->Unit
){
	val isSignUp = remember { mutableStateOf(false) }
	val email = remember { mutableStateOf<String>("") }
	val pass = remember { mutableStateOf<String>("") }
	val confirm = remember { mutableStateOf<String>("") }

	val formAnimActive = remember {mutableStateOf(false)}
	val off by moveElement(
		transformX = 400.dp,
		resetActive = true,
		active = formAnimActive,
		finishedListener = {
			isSignUp.value = !isSignUp.value
		}
	)

	val surfaceAnimActive = remember {mutableStateOf(false)}
	val offsetSurface by moveElement(
		transformY = 1500.dp,
		type = "vertical",
		active = surfaceAnimActive,
		finishedListener = {
			navController.navigate(DashboardScreen.route)
		}
	)


	Column(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight()
			.background(colorResource(R.color.grenTurq)),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		Column(
			modifier = Modifier
				.clip(shape = RoundedCornerShape(12.dp))
				.fillMaxHeight(0.3f)
				.padding(top = 24.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Image(
				modifier = Modifier.fillMaxSize(),
				painter = painterResource(R.drawable.logo),
				contentDescription = ""
			)
		}
		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.fillMaxHeight()
			.clip(shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp))
			.background(colorResource(R.color.offWhite))
			.offset { offsetSurface },
			color = colorResource(R.color.offWhite)
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.SpaceBetween
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 16.dp, end = 32.dp),
					horizontalArrangement = Arrangement.End
				) {
					Text(
						text = if(isSignUp.value) "Sign In Instead" else "Sign Up Instead",
						textAlign = TextAlign.End,
						fontSize = 15.sp,
						modifier = Modifier.clickable {
							formAnimActive.value = !formAnimActive.value
						},
						lineHeight = 15.sp,
						color = colorResource(R.color.darkGray)
					)
				}

				// Form
				Column(
					modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f).offset{off},
					verticalArrangement = Arrangement.SpaceAround,
					horizontalAlignment = Alignment.CenterHorizontally,
					) {
					Column(
						modifier = Modifier.fillMaxWidth(),
						horizontalAlignment = Alignment.CenterHorizontally,
						verticalArrangement = Arrangement.spacedBy(12.dp)) {

						Column(
							modifier = Modifier.fillMaxWidth(),
							horizontalAlignment = Alignment.CenterHorizontally,
							verticalArrangement = Arrangement.spacedBy(12.dp)
						) {
							Text(
								text = if (isSignUp.value) "Sign Up" else "Login",
								textAlign = TextAlign.Center,
								fontSize = 45.sp,
								fontWeight = FontWeight.Bold,
								modifier = Modifier.fillMaxWidth(0.8f),
								color = colorResource(R.color.darkTurq)
							)
							Text(
								"By continuing, you agree to our terms and conditions and have read our privacy policy",
								textAlign = TextAlign.Center,
								fontSize = 10.sp,
								modifier = Modifier.fillMaxWidth(0.5f),
								lineHeight = 10.sp,
								color = colorResource(R.color.darkGray)
							)
						}

						InputField(
							placeholderText = "Email",
							value = email,
							keyboardType = KeyboardType.Email,
							icon = R.drawable.mail
						)
						InputField(
							placeholderText = "Password",
							value = pass,
							keyboardType = KeyboardType.Password,
							icon = R.drawable.padlock,
							visualTransformation = PasswordVisualTransformation()
						)
						if(isSignUp.value){
							InputField(
								placeholderText = "Confirm Password",
								value = confirm,
								keyboardType = KeyboardType.Password,
								icon = R.drawable.padlock,
								visualTransformation = PasswordVisualTransformation()
							)
						}

						Row(
							Modifier.fillMaxWidth(),
							horizontalArrangement = Arrangement.SpaceAround
						) {
							Text(
								"Forgot Password?",
								textAlign = TextAlign.Center,
								fontSize = 10.sp,
								modifier = Modifier.fillMaxWidth(0.5f),
								lineHeight = 15.sp,
								color = colorResource(R.color.darkGray)
							)

						}
					}
				}

				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.Bottom
				) {
					Column(modifier = Modifier
						.height(150.dp)
						.width(150.dp)
						.clip(RoundedCornerShape(topEnd = 250.dp))
						.background(colorResource(R.color.lightTurq))
						.clickable {
							if(isSignUp.value && auth.currentUser == null){
								createAccount(
									email.value,
									pass.value,
									navController,
									{
										surfaceAnimActive.value = !surfaceAnimActive.value
									}
								)
							}
							if(!isSignUp.value && auth.currentUser == null){
								signInWithEmailAndPassword(
									email.value,
									pass.value,
									navController,
									{
										surfaceAnimActive.value = !surfaceAnimActive.value
									}
								)
							}
						}
						.padding(horizontal = if(isSignUp.value) 20.dp else 30.dp, vertical = 48.dp),
						horizontalAlignment = Alignment.Start,
						verticalArrangement = Arrangement.Bottom
					) {
						Text(
							text = if (isSignUp.value) "Sign Up" else "Login",
							fontSize = 22.sp,
							fontWeight = FontWeight.Bold,
							color = colorResource(R.color.darkGray)
						)
					}
				}
			}
		}
	}
}

//
//@Preview(showBackground = true)
//@Composable
//fun PreviewLogin(){
//	Scaffold(
//	) { innerPadding ->
//		Login(innerPadding, navController = rememberNavController())
//	}
//}