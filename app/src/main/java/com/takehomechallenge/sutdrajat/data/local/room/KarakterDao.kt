package com.takehomechallenge.sutdrajat.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter

@Dao
interface KarakterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(karakter: FavoriteKarakter)

    @Update
    fun update(karakter: FavoriteKarakter)

    @Delete
    fun delete(karakter: FavoriteKarakter)

    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllKarakter(): LiveData<List<FavoriteKarakter>>

    @Query("SELECT * FROM favorite WHERE name = :name")
    fun getFavoriteKarakterByName(name: String): LiveData<FavoriteKarakter>

    @Query("DELETE FROM favorite WHERE name = :name")
    fun deleteFavoriteKarakterByName(name: String)
}