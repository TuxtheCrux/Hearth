package com.scrux.gacrux.core.database

import android.content.Context
import com.scrux.gacrux.core.security.KeyStoreManager
import com.scrux.gacrux.feature.passwords.data.PasswordDao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        keyStoreManager: KeyStoreManager
    ): AppDatabase {
        return AppDatabase.getDatabase(context, keyStoreManager)
    }

    @Provides
    fun provideDatabaseDao(database: AppDatabase): PasswordDao {
        return database.passwordDao()
    }
}