package com.dev.test.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.test.model.CurrenciesHistory


@Database(entities = [CurrenciesHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun LocalDao(): LocalDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "currencies")
                .fallbackToDestructiveMigration()
                .build()
    }

}
