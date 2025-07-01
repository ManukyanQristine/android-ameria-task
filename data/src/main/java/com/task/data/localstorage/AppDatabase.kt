package com.task.data.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDetailsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}