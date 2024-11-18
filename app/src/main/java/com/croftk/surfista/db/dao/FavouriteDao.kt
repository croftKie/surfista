package com.croftk.surfista.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.croftk.surfista.db.entities.Favourite
import com.croftk.surfista.db.entities.GeoLocation

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite")
    fun getAll(): List<Favourite>

    @Query("SELECT * FROM favourite ORDER BY importance DESC")
    fun getAllSorted(): List<Favourite>

    @Query("SELECT * FROM favourite WHERE placeId = :id")
    fun checkIfInFavourites(id: Int): List<Favourite>

    @Insert
    fun insertFavourite(vararg favourite: Favourite)

    @Query("DELETE FROM favourite WHERE placeId = :id")
    fun deleteFavourite(id: Int): Int
}