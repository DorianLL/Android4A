package com.example.android4a.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android4a.local.models.UserLocal

/**
 * Init the app Database
 */
@Database(entities = arrayOf(UserLocal::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataBaseDao(): DatabaseDaoDao
}