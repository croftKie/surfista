package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.croftk.surfista.db.entities.GeoLocation

@Dao
interface GeolocationDao {
	@Query("SELECT * FROM geolocation")
	fun getAll(): List<GeoLocation>
}