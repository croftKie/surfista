package com.croftk.surfista.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.croftk.surfista.db.dao.GeolocationDao
import com.croftk.surfista.db.dao.UserDao
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.User

@Database(
	entities = [User::class, GeoLocation::class],
	version = 2,
	autoMigrations = [
		AutoMigration (from = 1, to = 2)
	],
	exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
	abstract fun userDao(): UserDao
	abstract fun GeolocationDao(): GeolocationDao
}