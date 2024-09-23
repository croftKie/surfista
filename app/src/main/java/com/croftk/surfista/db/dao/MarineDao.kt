package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.croftk.surfista.db.entities.Marine

@Dao
interface MarineDao {
	@Query("SELECT * FROM marine")
	fun getMarine(): List<Marine>

	@Insert
	fun insertMarineData(vararg  marine: Marine)

	@Query("DELETE FROM marine")
	fun deleteAll()
}