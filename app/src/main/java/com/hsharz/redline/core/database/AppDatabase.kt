package com.hsharz.redline.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hsharz.redline.feature.passwords.data.PasswordEntity
//import com.hsharz.redline.feature.cipher.data.AlgoEntity
import com.hsharz.redline.feature.passwords.data.PasswordDao

//import com.hsharz.redline.feature.scanner.data.ScanEntity

/**
 * Room-Datenbank der Redline App.
 *
 * Enthält alle lokalen Tabellen: Passwörter, Scans und Verschlüsselungs-Algorithmen.
 * Singleton-Zugriff über `getDatabase(context)`.
 */
@Database(
    entities = [PasswordEntity::class
        /**, ScanEntity::class, AlgoEntity::class**/
    ],
    version = 1, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    // DAOs für den Zugriff auf die einzelnen Tabellen
    abstract fun passwordDao(): PasswordDao
    //abstract fun scanDao(): ScanDao
    //abstract fun algoDao(): AlgoDao
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
