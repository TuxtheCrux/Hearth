package com.hsharz.redline.core.database

import android.content.Context
import com.hsharz.redline.feature.passwords.data.PasswordDao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideDatabaseDao(database: AppDatabase): PasswordDao {
        return database.passwordDao()
    }
}