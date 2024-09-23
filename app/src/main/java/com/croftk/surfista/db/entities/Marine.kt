package com.croftk.surfista.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Marine (
	@PrimaryKey val id: String,
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "lat") val lat: Double,
	@ColumnInfo(name = "lon") val lon: Double,
	@ColumnInfo(name = "time") val time: String,
	@ColumnInfo(name = "wave_height") val wave_height: String,
	@ColumnInfo(name = "wave_direction") val wave_direction: String,
	@ColumnInfo(name = "wave_period") val wave_period: String
	)