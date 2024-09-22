package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.croftk.surfista.db.entities.GeoLocation

@Dao
interface GeolocationDao {
	@Query("SELECT * FROM geolocation")
	fun getAll(): List<GeoLocation>

	@Query("SELECT * FROM geolocation ORDER BY importance DESC")
	fun getAllSorted(): List<GeoLocation>

	@Insert
	fun insertLocation(vararg location: GeoLocation)

	@Query("DELETE FROM geolocation")
	fun deleteAll()
}