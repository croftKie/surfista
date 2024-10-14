package com.croftk.surfista.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Temperature (
	@PrimaryKey val id: String,
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "lat") val lat: Double,
	@ColumnInfo(name = "lon") val lon: Double,
	@ColumnInfo(name = "time") val time: String,
	@ColumnInfo(name = "temperature") val temperature: String,
	@ColumnInfo(name = "rain") val rain: String,
	@ColumnInfo(name = "cloud_cover") val cloud_cover: String
)