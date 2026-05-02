package com.hsharz.redline.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hsharz.redline.feature.passwords.data.PasswordEntity
import com.hsharz.redline.feature.passwords.data.PasswordDao
import com.hsharz.redline.feature.passwords.data.WebOrAppTypeConverter

/**
 * Room-Datenbank der Redline App.
 *
 * Enthält alle lokalen Tabellen: Passwörter, Scans und Verschlüsselungs-Algorithmen.
 * Singleton-Zugriff über `getDatabase(context)`.
 * @author Tux_the_Crux
 */
//TODO implement class ScanEntity, class AlgoEntity
@TypeConverters(WebOrAppTypeConverter::class)
@Database(
    entities = [PasswordEntity::class],
    version = 2, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    // DAOs für den Zugriff auf die einzelnen Tabellen
    abstract fun passwordDao(): PasswordDao

    //TODO abstract fun scanDao(): ScanDao and abstract fun algoDao(): AlgoDao
    companion object {
        private const val DATABASE_NAME = "redline-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** Thread-sichere Singleton-Methode zum Holen der Datenbank-Instanz */
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context, AppDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration(true)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
