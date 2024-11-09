package com.croftk.surfista.screens

import android.content.ContentValues.TAG
import android.renderscript.ScriptGroup.Input
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.InputField
import com.croftk.surfista.components.SolidButton
import com.croftk.surfista.components.TabButton
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.User
import com.croftk.surfista.utilities.DashboardScreen
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KFunction2


@Composable
fun Login(
	innerPadding: PaddingValues,
	navController: NavController,
	db: AppDatabase,
	auth: FirebaseAuth,
	createAccount: (String, String, NavController)->Unit,
	signInWithEmailAndPassword: (String, String, NavController)->Unit
){
	val isSignUp = remember { mutableStateOf(false) }
	val email = remember { mutableStateOf<String>("") }
	val pass = remember { mutableStateOf<String>("") }
	val confirm = remember { mutableStateOf<String>("") }
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
			.background(colorResource(R.color.offWhite)),
			color = colorResource(R.color.offWhite)
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.SpaceAround
			) {
				Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
					InputField(
						placeholderText = "Email",
						value = email,
						keyboardType = KeyboardType.Email
					)
					InputField(
						placeholderText = "Password",
						value = pass,
						keyboardType = KeyboardType.Password,
						visualTransformation = PasswordVisualTransformation()
					)
					if(isSignUp.value){
						InputField(
							placeholderText = "Confirm Password",
							value = confirm,
							keyboardType = KeyboardType.Password,
							visualTransformation = PasswordVisualTransformation()
						)
					}
				}
				SolidButton(
					modifier = Modifier,
					text = if(isSignUp.value) "Sign Up" else "Sign In",
					iconActive = false,
				) {

					// SIGN UP LOGIC
					if(isSignUp.value && auth.currentUser == null){
						createAccount(email.value, pass.value, navController)
					}

					//SIGN IN LOGIC
					if(!isSignUp.value && auth.currentUser == null){
						signInWithEmailAndPassword(email.value, pass.value, navController)
					}
				}
				Text("Switch Mode", modifier = Modifier.clickable { isSignUp.value = !isSignUp.value })
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