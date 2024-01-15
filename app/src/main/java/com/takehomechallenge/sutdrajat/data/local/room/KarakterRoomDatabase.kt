package com.takehomechallenge.sutdrajat.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.takehomechallenge.sutdrajat.data.local.entity.FavoriteKarakter

@Database(entities = [FavoriteKarakter::class], version = 1)
abstract class KarakterRoomDatabase : RoomDatabase() {

    abstract fun favoriteKarakterDao(): KarakterDao

    companion object {
        @Volatile
        private var INSTANCE: KarakterRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): KarakterRoomDatabase {
            if (INSTANCE == null) {
                synchronized(RoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        KarakterRoomDatabase::class.java, "favorite_karakter_database")
                        .build()
                }
            }
            return INSTANCE as KarakterRoomDatabase
        }
    }
}