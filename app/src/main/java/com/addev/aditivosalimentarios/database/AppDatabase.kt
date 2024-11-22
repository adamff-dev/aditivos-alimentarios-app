package com.addev.aditivosalimentarios.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.addev.aditivosalimentarios.dao.AditivoDao
import com.addev.aditivosalimentarios.model.Aditivo

@Database(entities = [Aditivo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aditivoDao(): AditivoDao
}
