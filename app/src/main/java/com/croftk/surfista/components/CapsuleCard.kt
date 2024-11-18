package com.croftk.surfista.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.croftk.surfista.R
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.Board

@Composable
fun CapsuleCard(
    boardType: String = "",
    value: String,
    db: AppDatabase,
    rightContentBg: Color = colorResource(R.color.offWhite),
    radius: Dp = 12.dp,
    height: Dp = 60.dp,
){
    Row(modifier = Modifier
        .height(height)
        .fillMaxWidth(0.6f)
        .background(colorResource(R.color.offWhite)),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(radius)),
            border = BorderStroke(1.dp, colorResource(R.color.borderDark))
        ){
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(colorResource(R.color.lightGrey)),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    if(boardType.isNotEmpty()){
                        Text(color = colorResource(R.color.darkGray), text=boardType, fontSize = 23. sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(colorResource(R.color.lightTurq))
                                .padding(vertical = 4.dp, horizontal = 8.dp)

                        ) {
                            Text(text="${value}ft", fontSize = 15.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(colorResource(R.color.lightTurq))
                                .padding(vertical = 4.dp, horizontal = 8.dp)

                        ) {
                            Text(text="70 litres", fontSize = 15.sp)
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                )  {
                    ClickableIcon(
                        modifier = Modifier.height(30.dp),
                        drawableImage = R.drawable.add,
                        contentDesc = R.string.search_mag_desc,
                        click = {
                            db.BoardDao().insertBoard(
                                Board(
                                id = (1..100).random(),
                                name = "My Board",
                                type = boardType,
                                size = value
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}