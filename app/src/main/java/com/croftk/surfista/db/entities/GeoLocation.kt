package com.croftk.surfista.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GeoLocation(
	@PrimaryKey val placeId: Int,
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "lat") val lat: String,
	@ColumnInfo(name = "lon") val lon: String,
	@ColumnInfo(name = "importance") val importance: Float,
)
