package com.croftk.surfista.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wind (
	@PrimaryKey val id: String,
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "lat") val lat: Double,
	@ColumnInfo(name = "lon") val lon: Double,
	@ColumnInfo(name = "time") val time: String,
	@ColumnInfo(name = "visibility") val visibility: String,
	@ColumnInfo(name = "wind_speed") val wind_speed: String,
	@ColumnInfo(name = "wind_direction") val wind_direction: String
)