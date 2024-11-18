package com.croftk.surfista.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.croftk.surfista.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TogglableInput(
    width: Float = 0.8f,
    radius: Dp = 0.dp,
    placeholderText: String = "Search Surf Spot",
    icon: Int = R.drawable.mag,
    value: MutableState<String> = mutableStateOf(""),
    containerColor: Color = colorResource(R.color.lightGrey),
    focusedIndicatorColor: Color = colorResource(R.color.grenTurq),
    unfocusedIndicatorColor: Color = colorResource(R.color.darkTurq),
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    active: Boolean = false,
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
            if(active){
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
            }
        },
        readOnly = !active
    )
}