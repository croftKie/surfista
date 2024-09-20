package com.croftk.surfista.screens

import android.renderscript.ScriptGroup.Input
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.InputField
import com.croftk.surfista.components.TabButton
import com.croftk.surfista.utilities.DashboardScreen


@Composable
fun Login(innerPadding: PaddingValues, navController: NavController){
	var isSignUp = false
	var userNameValue = ""
	var passwordValue = ""
	var confirmValue = ""
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
		Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
			InputField(placeholderText = "Username")
			InputField(placeholderText = "Password")
			if(isSignUp){
				InputField(placeholderText = "Confirm Password")
			}
		}
		TabButton(
			modifier = Modifier,
			text = if(isSignUp) "Sign In" else "Login",
			iconActive = false,
			active = true
		) {
			navController.navigate(DashboardScreen.route)
		}
		Text("Switch Mode", modifier = Modifier.clickable { isSignUp = !isSignUp })
	}
}


@Preview(showBackground = true)
@Composable
fun PreviewLogin(){
	Scaffold(
	) { innerPadding ->
		Login(innerPadding, navController = rememberNavController())
	}
}