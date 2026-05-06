package com.hsharz.redline.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hsharz.redline.core.security.KeyStoreManager
import com.hsharz.redline.feature.passwords.data.EntryType
import com.hsharz.redline.feature.passwords.data.PasswordEntity
import com.hsharz.redline.feature.passwords.data.PasswordDao
import com.hsharz.redline.feature.passwords.data.WebOrAppTypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        private const val DATABASE_NAME = "redline-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        //TODO Testdaten löschen

        /** In AppDatabase, im Companion Object:
        private fun createCallback(context: Context) = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Wird nur beim allerersten Erstellen aufgerufen
                CoroutineScope(Dispatchers.IO).launch {
                    seedDatabase(getDatabase(context).passwordDao())
                }
            }
        }

        private suspend fun seedDatabase(dao: PasswordDao) {
            val testData = (1..50).map { i ->
                PasswordEntity(
                    websiteOrApp = if (i % 2 == 0) "https://site$i.com" else "App $i",
                    email = "user$i@test.de",
                    encryptedPassword = "passwort$i",
                    entryType = if (i % 2 == 0) EntryType.WEBSITE else EntryType.APP,
                    passkey = false,
                    lastModified = System.currentTimeMillis()
                )
            }
            testData.forEach { dao.insertPassword(it) }
        }

        //End Tesdata
        **/
        /** Thread-sichere Singleton-Methode zum Holen der Datenbank-Instanz */
        fun getDatabase(context: Context, keyStoreManager: KeyStoreManager): AppDatabase {
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
    }
}

