package com.task.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_details WHERE login = :username")
    fun observeUserByLogin(username: String): Flow<UserDetailsEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserDetailsEntity)
}
