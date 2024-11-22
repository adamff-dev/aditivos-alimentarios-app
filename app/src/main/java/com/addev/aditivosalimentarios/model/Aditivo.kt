package com.addev.aditivosalimentarios.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aditivos")
data class Aditivo (
    @PrimaryKey val numero: String,
    val nombre: String,
    val toxicidad: String
)