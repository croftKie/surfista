package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.croftk.surfista.db.entities.Temperature

@Dao
interface TempDao {
	@Query("SELECT * FROM temperature")
	fun getTempData(): List<Temperature>

	@Insert
	fun insertTempData(vararg  temperature: Temperature)

	@Query("DELETE FROM temperature")
	fun deleteAll()
}