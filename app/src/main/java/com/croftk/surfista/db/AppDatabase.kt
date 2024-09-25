package com.croftk.surfista.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.croftk.surfista.db.dao.BoardDao
import com.croftk.surfista.db.dao.GeolocationDao
import com.croftk.surfista.db.dao.MarineDao
import com.croftk.surfista.db.dao.UserDao
import com.croftk.surfista.db.entities.Board
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.db.entities.User

@Database(
	entities = [User::class, GeoLocation::class, Marine::class, Board::class],
	version = 4,
//	autoMigrations = [
//		AutoMigration (from = 1, to = 2)
//	],
	exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao
	abstract fun GeolocationDao(): GeolocationDao
	abstract fun MarineDao(): MarineDao
	abstract fun BoardDao(): BoardDao
}