package com.croftk.surfista.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Board (
	@PrimaryKey(autoGenerate = true) val id: Int,
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "type") val type: String,
	@ColumnInfo(name = "size") val size: String,
)