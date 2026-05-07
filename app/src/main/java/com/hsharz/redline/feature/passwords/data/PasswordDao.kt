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

    @Query("DELETE FROM PasswordEntity WHERE id = :id")
    suspend fun deletePassword(id: Long)

    @Query("SELECT * FROM PasswordEntity")
    fun getAllPasswords(): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM PasswordEntity WHERE id = :id")
    suspend fun getPasswordById(id: Long): PasswordEntity?
}