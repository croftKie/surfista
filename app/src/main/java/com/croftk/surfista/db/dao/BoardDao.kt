package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.croftk.surfista.db.entities.Board

@Dao
interface BoardDao {
	@Query("SELECT * FROM board")
	fun getAll(): List<Board>

	@Insert
	fun insertBoard(vararg board: Board)

	@Query("DELETE FROM board")
	fun deleteAll()
}