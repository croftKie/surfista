package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.croftk.surfista.db.entities.User

@Dao
interface UserDao {
	@Query("SELECT * FROM user")
	fun getAll(): List<User>

	@Query("SELECT * FROM user WHERE uid = :userId")
	fun loadUserById(userId: Int): List<User>

	@Query("SELECT * FROM user WHERE email = :email AND password = :password")
	fun checkUserExists(email: String, password: String): List<User>

	@Query("SELECT loggedIn FROM user WHERE uid = :userId")
	fun checkIsLoggedIn(userId: Int): Boolean

	@Update
	fun updateUser(user: User)

	@Insert
	fun insertUser(vararg user: User)

	@Delete
	fun deleteUser(user: User)
}