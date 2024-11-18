package com.croftk.surfista.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
fun ClickableIcon(
	modifier: Modifier = Modifier,
	drawableImage: Int,
	contentDesc: Int,
	click: ()-> Unit,
	lineActive: Boolean = false
){
	Column(modifier = modifier.clip(CircleShape).padding(4.dp).clickable(
		interactionSource = remember { MutableInteractionSource() },
		indication = rememberRipple(
			bounded = true,
			radius = 10.dp,
			color = colorResource(R.color.darkTurq)
		)) {
		click()
	},
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center){
		Icon(
			painter = painterResource(drawableImage),
			contentDescription = stringResource(id = contentDesc)
		)
	}
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
fun SolidButton(
	modifier: Modifier = Modifier,
	text: String,
	fontSize: TextUnit = 15.sp,
	drawable: Int = R.drawable.ic_launcher_foreground,
	iconActive: Boolean = true,
	onClick: () -> Unit
){
	Card(
		modifier = modifier
			.widthIn(30.dp, 100.dp)
			.height(30.dp)
			.clickable(enabled = true, onClick = onClick),
		shape = RoundedCornerShape(6.dp),
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(30.dp)
				.background(colorResource(R.color.grenTurq))
				.padding(3.dp),
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
			Text(color = colorResource(R.color.offWhite), text = text, fontSize = fontSize)
		}
	}
}

@Composable
fun NavigationBar(
	leftOptionOnClick: ()->Unit = {},
	centerOptionOnClick: ()->Unit = {},
	rightOptionOnClick: ()->Unit = {}
){
	val activeOption = remember { mutableIntStateOf(1) }

	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceAround
		) {
		ClickableIcon(
			Modifier
				.height(40.dp)
				.width(40.dp)
				.alpha(if(activeOption.value == 0) 1f else 0.5f),
			R.drawable.surfboard,
			R.string.search_mag_desc,
			click = {
				activeOption.value = 0
				leftOptionOnClick()
			},
		)
		ClickableIcon(
			Modifier
				.height(40.dp)
				.width(40.dp)
				.alpha(if(activeOption.value == 1) 1f else 0.2f),
			R.drawable.logo,
			R.string.search_mag_desc,
			click = {
				activeOption.value = 1
				centerOptionOnClick()
			},
		)
		ClickableIcon(
			Modifier
				.height(35.dp)
				.width(35.dp)
				.alpha(if(activeOption.value == 2) 1f else 0.2f),
			R.drawable.setting,
			R.string.search_mag_desc,
			click = {
				activeOption.value = 2
				rightOptionOnClick()
			},
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
	radius: Dp = 0.dp,
	placeholderText: String = "Search Surf Spot",
	icon: Int = R.drawable.mag,
	value: MutableState<String> = mutableStateOf(""),
	containerColor: Color = colorResource(R.color.offWhite),
	focusedIndicatorColor: Color = colorResource(R.color.grenTurq),
	unfocusedIndicatorColor: Color = colorResource(R.color.darkTurq),
	keyboardType: KeyboardType = KeyboardType.Text,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	onClick: (MutableState<String>) -> Unit = {}
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
			Text(modifier = Modifier.alpha(0.4f), text = placeholderText)
		},
		keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
		visualTransformation = visualTransformation,
		trailingIcon = {
			ClickableIcon(
				Modifier
					.height(25.dp)
					.width(25.dp)
					.alpha(0.4f),
				icon,
				R.string.search_mag_desc,
				click = {
					onClick(value)
				}
			)
		},
	)
}

@Composable
fun SearchBar(
	adjustablePadding: Dp = 12.dp,
	onClick: (text: MutableState<String>) -> Unit = {},
	value: MutableState<String> = mutableStateOf(""),
	buttonActive: Boolean = true,
	searchBarActive: Boolean = true,
	placeholder: String = "Search Surf Spot"
) {
	InputField(placeholderText = placeholder, value = value, width = 1.0f, onClick = onClick)
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
			drawableImage = R.drawable.logo,
			contentDesc = R.string.search_mag_desc
		)
		Text(modifier = Modifier.alpha(0.5f), text = text, fontSize = 20.sp)
	}
}

@Composable
fun Tutorial(modifier: Modifier = Modifier, mode: Int = 0){
	Column(
		modifier = modifier.fillMaxWidth().height(200.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	){
		ImageIcon(
			Modifier.fillMaxSize().alpha(0.25f),
			drawableImage = R.drawable.tutone,
			contentDesc = R.string.search_mag_desc
		)
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