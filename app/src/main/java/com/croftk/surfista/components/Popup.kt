package com.croftk.surfista.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.croftk.surfista.R

@Composable
fun Popup(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: Int,
    mode: Int,
    placeholderText: String,

) {
    val value = remember { mutableStateOf("") }

    Dialog(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.3f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(R.color.offWhite))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxHeight(0.2f).padding(top = 18.dp)
                    ) {
                        Icon(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            painter = painterResource(icon),
                            contentDescription = ""
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(0.7f).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(dialogTitle, fontSize = 25.sp)
                        if(mode == 0){
                            Text(dialogText, textAlign = TextAlign.Center)
                        } else if (mode == 1){
                            InputField(placeholderText = placeholderText, value = value)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.End)
                    ) {
                        ClickableIcon(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            drawableImage = R.drawable.downarrow,
                            contentDesc = R.string.search_mag_desc,
                            click = {
                                onDismissRequest()
                            }
                        )
                        ClickableIcon(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            drawableImage = R.drawable.uparrow,
                            contentDesc = R.string.search_mag_desc,
                            click = {
                                onConfirmation(value.value)
                            }
                        )
                    }
                }
            }
        },
        onDismissRequest = {onDismissRequest()},
        properties = DialogProperties()
    )
}