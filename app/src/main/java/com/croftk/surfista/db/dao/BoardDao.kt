package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.croftk.surfista.db.entities.Board
import com.croftk.surfista.db.entities.User

@Dao
interface BoardDao {
	@Query("SELECT * FROM board")
	fun getAll(): List<Board>

	@Insert
	fun insertBoard(vararg board: Board)

	@Update
	fun updateBoard(board: Board)

	@Query("DELETE FROM board")
	fun deleteAll()
}