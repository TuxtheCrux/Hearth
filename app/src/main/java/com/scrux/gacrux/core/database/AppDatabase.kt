package com.scrux.gacrux.core.database

import android.content.Context
import androidx.core.content.edit
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scrux.gacrux.core.security.KeyStoreManager
import com.scrux.gacrux.feature.passwords.data.PasswordEntity
import com.scrux.gacrux.feature.passwords.data.PasswordDao
import com.scrux.gacrux.feature.passwords.data.WebOrAppTypeConverter
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

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
        private const val DATABASE_NAME = "gacrux-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** Thread-sichere Singleton-Methode zum Holen der Datenbank-Instanz */
        fun getDatabase(context: Context, keyStoreManager: KeyStoreManager): AppDatabase {
            // Pending Reset prüfen
            val systemPrefs = context.getSharedPreferences("RedlineSystem", Context.MODE_PRIVATE)
            if (systemPrefs.getBoolean("pending_reset", false)) {
                context.deleteDatabase(DATABASE_NAME)
                systemPrefs.edit { putBoolean("pending_reset", false) }
            }

            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        val password = keyStoreManager.getOrCreateDatabasePassword()
                        val factory = SupportOpenHelperFactory(password)
                        INSTANCE = Room.databaseBuilder(
                            context, AppDatabase::class.java,
                            DATABASE_NAME
                        )
                            .openHelperFactory(factory)
                            .fallbackToDestructiveMigration(true)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun resetInstance() {
            INSTANCE = null
        }
    }
}

