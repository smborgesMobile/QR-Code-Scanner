package com.br.smobile.com.result.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.br.smobile.com.result.model.ResultModel

@Database(entities = [ResultModel::class], version = 1)
abstract class AppDataBase() : RoomDatabase() {

    abstract fun resultAPI(): ResultDAO

    companion object {
        private const val DB_NAME = "Result-Database.db"

        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME).build()
    }
}