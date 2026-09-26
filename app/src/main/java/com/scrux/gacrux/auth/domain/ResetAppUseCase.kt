package com.scrux.gacrux.auth.domain

import android.content.Context
import androidx.core.content.edit
import com.scrux.gacrux.auth.data.AuthRepository
import com.scrux.gacrux.core.database.AppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ResetAppUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    val authRepository: AuthRepository,
    val appDatabase: AppDatabase
) {

    suspend fun invoke() = withContext(Dispatchers.IO) {
        context.getSharedPreferences("RedlineSystem", Context.MODE_PRIVATE)
            .edit { putBoolean("pending_reset", true) }
        authRepository.clearPreferences()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}