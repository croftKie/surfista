package com.croftk.surfista.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.croftk.surfista.R
import com.croftk.surfista.components.CapsuleCard
import com.croftk.surfista.components.ClickableIcon
import com.croftk.surfista.components.Empty
import com.croftk.surfista.components.HorizontalCard
import com.croftk.surfista.components.ImageIcon
import com.croftk.surfista.components.NavigationBar
import com.croftk.surfista.components.QuiverFilterModifier
import com.croftk.surfista.components.SearchBar
import com.croftk.surfista.components.SearchResultModifier
import com.croftk.surfista.components.SolidButton
import com.croftk.surfista.components.TitleBar
import com.croftk.surfista.components.VerticalCard
import com.croftk.surfista.db.AppDatabase
import com.croftk.surfista.db.entities.Board

@Composable
fun BoardCardRow(adjustablePadding: Dp, quiver: MutableState<List<Board>>, db: AppDatabase){
	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.padding(12.dp),
		verticalArrangement = Arrangement.spacedBy(18.dp)
	) {
		Row(modifier = Modifier
			.padding(bottom = adjustablePadding, start = adjustablePadding / 2).fillMaxWidth()
			.horizontalScroll(scrollState),
			horizontalArrangement = if(quiver.value.isNotEmpty()) Arrangement.spacedBy(12.dp) else Arrangement.Center
		) {
			if (quiver.value.isNotEmpty()){
				quiver.value.forEachIndexed{_, board->
					VerticalCard(
						board = board,
						clickToSave = { name ->
							val newBoard = Board(
								id = board.id,
								name = name,
								type = board.type,
								size = board.size
							)
							db.BoardDao().updateBoard(newBoard)
						}
					)
				}
			} else {
				Empty(text = "No surfboards added")
			}
		}
	}
}

@Composable
fun Quiver(innerPadding: PaddingValues, navController: NavController, db: AppDatabase){
	val vertScrollState = rememberScrollState()
	val boards: List<Map<String, List<String>>> = listOf(
		mapOf(
			"Fish" to listOf("5'6", "5'8", "5'10", "6'0", "6'2", "6'4")
		),
		mapOf(
			"Shortboard" to listOf("5'10", "6'0", "6'2", "6'4", "6'6", "6'8", "6'10")
		),
		mapOf(
			"Midlength" to listOf("7'0", "7'2", "7'4", "7'6", "7'8", "7'10")
		),
		mapOf(
			"Longboard" to listOf("8'0", "8'2", "8'4", "8'6", "8'8", "8'10", "9'0", "9'2", "9'4", "9'6", "9'8", "9'10", "10'0")
		),
	)
	val searchInput = remember { mutableStateOf("") }

	val myQuiver = remember { mutableStateOf(db.BoardDao().getAll()) }


	Column(
		modifier = Modifier.fillMaxHeight().fillMaxWidth().background(colorResource(R.color.grenTurq)).padding(innerPadding),
		horizontalAlignment = Alignment.CenterHorizontally
	){
		BoardCardRow(12.dp, myQuiver, db)

		Column(
			modifier = Modifier
				.verticalScroll(vertScrollState)
				.fillMaxWidth()
				.clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
				.background(colorResource(R.color.offWhite))
				.padding(12.dp),
			verticalArrangement = Arrangement.spacedBy(32.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			SearchBar(
				12.dp,
				value = searchInput,
				buttonActive = true,
				placeholder = "Filter Surfboards",
				onClick = {
						updatedText ->
					searchInput.value = updatedText.value
				}
			)
			boards.forEachIndexed{index, boardType ->
				boardType.entries.forEach{ entry ->
					val horiScrollState = rememberScrollState()

					Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
						Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
							Text(text = entry.key, fontSize = 30.sp)
							Text(text = "Lorem ipsum text would be placed here as a description of the board.")
						}
						Row(
							modifier = Modifier.horizontalScroll(horiScrollState),
							horizontalArrangement = Arrangement.spacedBy(24.dp)
						) {
							entry.value.forEach { value ->
								if (
									entry.key.lowercase().contains(searchInput.value.lowercase()) ||
									value.lowercase().contains(searchInput.value.lowercase())
								){
									CapsuleCard(value = value, db = db, height = 60.dp)
								}
							}
						}
					}
				}
			}
		}
	}
}