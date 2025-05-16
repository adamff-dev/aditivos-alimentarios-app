package com.addev.aditivosalimentarios.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "alt_names",
    foreignKeys = [ForeignKey(
        entity = Additive::class,
        parentColumns = ["id"],
        childColumns = ["additive_id"],
        onDelete = ForeignKey.NO_ACTION  // matches your DB
    )]
)
data class AltName(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "alt_name")
    val altName: String,

    @ColumnInfo(name = "additive_id")
    val additiveId: Int,

    @ColumnInfo(name = "lang")
    val lang: String
)

