package com.croftk.surfista.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.croftk.surfista.R

@Composable
fun ClickableIcon(modifier: Modifier = Modifier, drawableImage: Int, contentDesc: Int, click: ()-> Unit){
	Icon(
		modifier = modifier.clickable {
			click()
		},
		painter = painterResource(drawableImage),
		contentDescription = stringResource(id = contentDesc)
	)
}
@Composable
fun ImageIcon(modifier: Modifier = Modifier, drawableImage: Int, contentDesc: Int){
	Icon(
		modifier = modifier,
		painter = painterResource(drawableImage),
		contentDescription = stringResource(id = contentDesc)
	)
}
@Composable
fun TabButton(
	modifier: Modifier = Modifier,
	text: String,
	color: Color = Color.DarkGray,
	height: Dp = 1.dp,
	width: Dp = 60.dp,
	fontSize: TextUnit = 20.sp,
	drawable: Int = R.drawable.ic_launcher_foreground,
	active: Boolean = false,
	iconActive: Boolean = true,
	onClick: () -> Unit
){

	val bgColor = if (active) R.color.sand else R.color.white

	Column(
		modifier = modifier
			.widthIn(60.dp, 120.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.background(colorResource(bgColor))
			.clickable(enabled = true, onClick = onClick),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(6.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			if(iconActive){
				ImageIcon(
					modifier = Modifier
						.height(20.dp)
						.width(20.dp),
					drawableImage = drawable,
					contentDesc = R.string.search_mag_desc
				)
			}
			Text(text = text, fontSize = fontSize)
		}
	}
}

@Composable
fun NavigationBar(
	leftOptionOnClick: ()->Unit = {},
	centerOptionOnClick: ()->Unit = {},
	rightOptionOnClick: ()->Unit = {}
){
	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceAround
		) {
		ClickableIcon(
			Modifier
				.height(30.dp)
				.width(30.dp),
			R.drawable.surfboard,
			R.string.search_mag_desc,
			leftOptionOnClick
		)
		ClickableIcon(
			Modifier
				.height(30.dp)
				.width(30.dp),
			R.drawable.wave,
			R.string.search_mag_desc,
			centerOptionOnClick
		)
		ClickableIcon(
			Modifier
				.height(30.dp)
				.width(30.dp),
			R.drawable.setting,
			R.string.search_mag_desc,
			rightOptionOnClick
		)
	}
}

@Composable
fun TitleBar(
	icon: Int = R.drawable.surfboard,
	iconDesc: Int = R.string.search_mag_desc,
	iconSize: Dp = 40.dp,
	actionIcon: Int = R.drawable.add,
	actionIconDesc: Int = R.string.search_mag_desc,
	actionIconSize: Dp = 40.dp,
	fontSize: TextUnit = 30.sp,
	padding: Dp = 6.dp,
	actionButton: Boolean = false,
	actionClick: ()->Unit = {}
){
	Row(
		Modifier
			.fillMaxWidth()
			.padding(padding),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	){
		ImageIcon(Modifier.height(iconSize), icon, iconDesc)
		if (actionButton) {
			ClickableIcon(
				modifier = Modifier.height(actionIconSize),
				drawableImage = actionIcon,
				contentDesc = actionIconDesc,
				click = {}
			)
		} else {
			Box(modifier = Modifier
				.height(40.dp)
				.width(40.dp)){}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
	width: Float = 0.8f,
	radius: Dp = 12.dp,
	placeholderText: String = "Search Surf Spot",
	value: MutableState<String> = mutableStateOf(""),
	containerColor: Color = Color.White,
	focusedIndicatorColor: Color = Color.White,
	unfocusedIndicatorColor: Color = Color.White,
	keyboardType: KeyboardType = KeyboardType.Text,
	visualTransformation: VisualTransformation = VisualTransformation.None
){
	TextField(
		modifier = Modifier
			.fillMaxWidth(fraction = width)
			.clip(shape = RoundedCornerShape(radius)),
		colors = TextFieldDefaults.textFieldColors(
			containerColor = containerColor,
			focusedIndicatorColor = focusedIndicatorColor,
			unfocusedIndicatorColor = unfocusedIndicatorColor
		),
		value = value.value,
		onValueChange = { value.value = it },
		placeholder = {
			Text(placeholderText)
		},
		keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
		visualTransformation = visualTransformation
	)
}

@Composable
fun SearchBar(
	adjustablePadding: Dp = 12.dp,
	onClick: (text: MutableState<String>) -> Unit = {},
	value: MutableState<String> = mutableStateOf(""),
	buttonActive: Boolean = true,
) {

	Row(
		modifier = Modifier
			.padding(adjustablePadding)
			.fillMaxWidth()
			.height(55.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp)
	){
		InputField(value = value, width = if(buttonActive) 0.8f else 1.0f)
		if(buttonActive){
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.fillMaxHeight()
					.clip(shape = RoundedCornerShape(12.dp))
					.background(colorResource(R.color.blue)),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			){
				ClickableIcon(
					Modifier
						.fillMaxWidth()
						.height(20.dp)
						.width(20.dp),
					R.drawable.mag,
					R.string.search_mag_desc,
					click = {
						onClick(value)
					}
				)
			}
		}
	}
}


@Composable
fun Empty(modifier: Modifier = Modifier, text: String = "No Data Selected Yet"){
	Column(
		modifier = modifier.fillMaxWidth().height(200.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		ImageIcon(
			Modifier.height(90.dp).alpha(0.25f).padding(bottom = 12.dp),
			drawableImage = R.drawable.surfboard,
			contentDesc = R.string.search_mag_desc
		)
		Text(modifier = Modifier.alpha(0.5f), text = text, fontSize = 20.sp)
	}
}


// Previews

@Preview(showBackground = true)
@Composable
fun PreviewEmpty(){
	Empty()
}

@Preview(showBackground = true)
@Composable
fun PreviewTabButton(){
	TabButton(text = "Click") {
		println("Hello World")
	}
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigationBar(){
	NavigationBar()
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleBar(){
	TitleBar()
}

@Preview()
@Composable
fun PreviewSearchBar(){
	SearchBar()
}