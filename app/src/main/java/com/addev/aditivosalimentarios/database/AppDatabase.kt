package com.addev.aditivosalimentarios.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.addev.aditivosalimentarios.dao.AditivoDao
import com.addev.aditivosalimentarios.model.Additive
import com.addev.aditivosalimentarios.model.AdditiveWithAltNames
import com.addev.aditivosalimentarios.model.AltName

@Database(entities = [Additive::class, AltName::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aditivoDao(): AditivoDao
}