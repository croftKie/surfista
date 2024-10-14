package com.croftk.surfista.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.croftk.surfista.db.dao.BoardDao
import com.croftk.surfista.db.dao.GeolocationDao
import com.croftk.surfista.db.dao.MarineDao
import com.croftk.surfista.db.dao.TempDao
import com.croftk.surfista.db.dao.UserDao
import com.croftk.surfista.db.dao.WindDao
import com.croftk.surfista.db.entities.Board
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.db.entities.Temperature
import com.croftk.surfista.db.entities.User
import com.croftk.surfista.db.entities.Wind

@Database(
	entities = [User::class, GeoLocation::class, Marine::class, Board::class, Temperature::class, Wind::class],
	version = 8,
//	autoMigrations = [
//		AutoMigration (from = 1, to = 2)
//	],
	exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao
	abstract fun GeolocationDao(): GeolocationDao
	abstract fun MarineDao(): MarineDao
	abstract fun BoardDao(): BoardDao
	abstract fun TempDao(): TempDao
	abstract fun WindDao(): WindDao
}