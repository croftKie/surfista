package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.croftk.surfista.db.entities.Wind

@Dao
interface WindDao {
	@Query("SELECT * FROM wind")
	fun getWindData(): List<Wind>

	@Insert
	fun insertWindData(vararg  wind: Wind)

	@Query("DELETE FROM wind")
	fun deleteAll()
}