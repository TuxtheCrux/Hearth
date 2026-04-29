package com.hsharz.redline.feature.passwords.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface PasswordDao {
    @Insert
    suspend fun insertPassword(vararg password: PasswordEntity)

    @Update
    suspend fun updatePassword(vararg password: PasswordEntity)

    @Delete
    suspend fun deletePassword(vararg password: PasswordEntity)

    @Query("SELECT * FROM passwordentity")
    fun getAllPasswords(): Flow<List<PasswordEntity>>
}